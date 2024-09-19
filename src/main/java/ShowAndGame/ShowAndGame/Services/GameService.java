package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetGameDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetGameForExploreDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserDev;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserDevRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserDevRepository userDevRepository;
    private final UserRepository userRepository;
    @Autowired
    public GameService(GameRepository gameRepository, UserDevRepository userDevRepository, UserRepository userRepository){
        this.gameRepository = gameRepository;
        this.userDevRepository = userDevRepository;
        this.userRepository = userRepository;
    }

    public void Create(GameForCreationAndUpdateDto newGame, Long userDevId, List<Tag> tags) {
        Game gameToCreate = new Game();
        Optional<UserDev> dev = userDevRepository.findById(userDevId);
        UserDev currentDev = null;

        if (dev.isPresent()){
            currentDev = dev.get();
        }

        gameToCreate.setName(newGame.getName());
        gameToCreate.setDescription(newGame.getDescription());
        gameToCreate.setProfileImage(newGame.getProfileImage());
        gameToCreate.setBackgroundImage(newGame.getBackgroundImage());
        gameToCreate.setRating(newGame.getRating());
        gameToCreate.setFollowers(newGame.getFollowers());
        gameToCreate.setFollowerAmount(newGame.getFollowerAmount());
        gameToCreate.setOwner(currentDev);
        gameToCreate.setTags(tags);


        gameRepository.save(gameToCreate);
    }

    public void Delete(Long id) {
        gameRepository.deleteById(id);
    }

    public Optional<Game> GetById(Long id) {
        return gameRepository.findById(id);
    }

    public List<GetGameDto> GetAll() {
        List<Game> allGames = gameRepository.findAll();
        return allGames.stream()
                .map(GetGameDto::new)
                .collect(Collectors.toList());
    }

    public List<GetGameForExploreDto> GetAllForFeed() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(GetGameForExploreDto::new)
                .collect(Collectors.toList());
    }

    public void Update(GetGameDto gameToUpdate) {
        Optional<Game> currentGame = gameRepository.findById(gameToUpdate.getId());
        Game game = null;

        if(currentGame.isPresent()){
            game = currentGame.get();
            game.setName(gameToUpdate.getName());
            game.setProfileImage(gameToUpdate.getProfileImage());
            game.setBackgroundImage(gameToUpdate.getBackgroundImage());
            game.setDescription(gameToUpdate.getDescription());
            game.setTags(gameToUpdate.getTags());
            gameRepository.save(game);
        }

        // desde aca hay que actualizar la lista de tags
    }

    // Habria que chequear como maneja el que no este el usuario o el game

    //asegura que todas las operaciones dentro del método se ejecuten como una única transacción, si falla se revertirá todoo
    @Transactional
    public void Follow(Long userId, Long gameId){
        Optional<User> currentUser = userRepository.findById(userId);
        Optional<Game> currentGame = gameRepository.findById(gameId);
        Game game = null;
        User user = null;

        if(currentGame.isPresent()){
            game = currentGame.get();
        }

        if(currentUser.isPresent()){
            user = currentUser.get();
        }

        game.getFollowers().add(user);
        user.getFollowedGames().add(game);
        game.setFollowerAmount(game.getFollowerAmount() + 1);

        gameRepository.save(game);
        userRepository.save(user);

    }

    @Transactional
    public void Unfollow(Long userId, Long gameId){
        Optional<User> currentUser = userRepository.findById(userId);
        Optional<Game> currentGame = gameRepository.findById(gameId);
        Game game = null;
        User user = null;

        if(currentGame.isPresent()){
            game = currentGame.get();
        }

        if(currentUser.isPresent()){
            user = currentUser.get();
        }

        game.getFollowers().remove(user);
        user.getFollowedGames().remove(game);
        game.setFollowerAmount(game.getFollowerAmount() - 1);

        gameRepository.save(game);
        userRepository.save(user);

    }
}
