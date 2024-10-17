package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.*;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.TagRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final FollowService followService;
    @Autowired
    public GameService(GameRepository gameRepository, UserRepository userRepository, TagRepository tagRepository, FollowService followService){
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.followService = followService;
    }

    public void Create(GameForCreationDto newGame, Long userId) {
        Game gameToCreate = new Game();
        Optional<User> dev = userRepository.findById(userId);
        User currentDev = null;
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

    public GetGameDto GetById(Long gameId, Long userId) {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isPresent()){
            boolean isFollowed = followService.isFollowedCheck(userId, gameId);
            return new GetGameDto(game.get(), isFollowed);
        }
        else {
            return null;
        }
    }

    public List<GetGameCardDto> GetAll() {
        List<Game> allGames = gameRepository.findAll();
        return allGames.stream()
                .map(GetGameCardDto::new)
                .collect(Collectors.toList());
    }

    public List<GetGameCardDto> GetAllForExplore() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(GetGameCardDto::new)
                .collect(Collectors.toList());
    }

    public List<GetGameDto> GetGameForUserProfile(Long userId) {
        return gameRepository.findByFollows_UserWhoFollowed_Id(userId).stream()
                .map(game -> {
                  boolean isFollowed = followService.isFollowedCheck(userId, game.getId());
                  return new GetGameDto(game, isFollowed);
                }).collect(Collectors.toList());
    }

    public List<GetGamesForDeveloperDto> getGamesByDeveloper(Long devId) {
        return gameRepository.findByOwnerId(devId).stream()
                .map(GetGamesForDeveloperDto::new)
                .collect(Collectors.toList());
    }

    public void Update(GetGamesForDeveloperDto gameToUpdate) {
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
}
