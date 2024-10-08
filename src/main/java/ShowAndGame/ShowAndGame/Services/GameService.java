package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GameForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGameDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGameForExploreDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGamesByUserDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.TagRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserDevRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserDevRepository userDevRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    @Autowired
    public GameService(GameRepository gameRepository, UserDevRepository userDevRepository, UserRepository userRepository, TagRepository tagRepository){
        this.gameRepository = gameRepository;
        this.userDevRepository = userDevRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public void Create(GameForCreationDto newGame, Long userDevId) {
        Game gameToCreate = new Game();
        Optional<UserDev> dev = userDevRepository.findById(userDevId);
        UserDev currentDev = null;
        List<Long> tags = newGame.getTagsId();

        List<Tag> tagsForCreation = tags.stream().map(tag -> tagRepository.findById(tag).get()).toList();

        if (dev.isPresent()){
            currentDev = dev.get();
        }

        gameToCreate.setName(newGame.getName());
        gameToCreate.setDescription(newGame.getDescription());
        gameToCreate.setProfileImage(newGame.getProfileImage());
        gameToCreate.setBackgroundImage(newGame.getBackgroundImage());
        gameToCreate.setRating(0);
        gameToCreate.setFollowers(new ArrayList<User>());
        gameToCreate.setFollowerAmount(0);
        gameToCreate.setOwner(currentDev);
        gameToCreate.setTags(tagsForCreation);

        gameRepository.save(gameToCreate);
    }

    public void Delete(Long id, Long userDevId) {
        Optional<Game> currentGame = gameRepository.findById(id);
        Game game = null;

        if(currentGame.isPresent()){
            game = currentGame.get();
            if (Objects.equals(game.getOwner().getId(), userDevId)){
                gameRepository.deleteById(id);
            }
        }
    }

    public GetGameDto GetById(Long id) {
        Optional<Game> game = gameRepository.findById(id);
        Game currentGame = null;

        if (game.isPresent()){
            currentGame = game.get();
        }

        return new GetGameDto(currentGame);
    }

    public List<GetGameDto> GetAll() {
        List<Game> allGames = gameRepository.findAll();
        return allGames.stream()
                .map(GetGameDto::new)
                .collect(Collectors.toList());
    }

    public List<GetGameForExploreDto> GetAllForExplore() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(GetGameForExploreDto::new)
                .collect(Collectors.toList());
    }

    public List<GetGamesByUserDto> GetGameByUser(Long userId) {
        List<Game> games = gameRepository.findByFollowersId(userId);
        return games.stream()
                .map(GetGamesByUserDto::new)
                .collect(Collectors.toList());
    }

    public List<GetGamesByUserDto> GetGameByDeveloper(Long userId) {
        List<Game> games = gameRepository.findByOwnerId(userId);
        return games.stream()
                .map(GetGamesByUserDto::new)
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
    }

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
