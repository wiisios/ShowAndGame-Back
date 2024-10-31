package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.*;
import ShowAndGame.ShowAndGame.Persistence.Dto.TagDto.*;
import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.ReviewPostForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.TagRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ShowAndGame.ShowAndGame.Util.GameReportGenerator;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    private GameReportGenerator gameReportGenerator;
    @Autowired
    public GameService(GameRepository gameRepository, UserRepository userRepository, TagRepository tagRepository, FollowService followService){
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.followService = followService;
    }

    public GetGameDto GetById(Long gameId, Long userId) {
        Optional<GetGameDto> gameDtoOptional = gameRepository.findGameWithOwnerAndFollowStatus(gameId, userId);

        if (gameDtoOptional.isPresent()) {
            GetGameDto gameDto = gameDtoOptional.get();

            List<Tag> tags = tagRepository.findTagsByGameId(gameId);

            gameDto.setTags(tags.stream().map(GetTagDto::new).collect(Collectors.toList())); // Asumiendo que tienes un constructor en GetTagDto
            return gameDto;
        }

        return null;
    }


    public List<Game> GetAll() {
        return gameRepository.findAll();
    }

    public List<GetGameCardDto> GetAllForExplore() {
        List<Game> games = gameRepository.findAllForExplore();

        //This Method returns a List of GetGameCardDto for the explore page
        return games.stream()
                .map(GetGameCardDto::new)
                .collect(Collectors.toList());
    }

    public List<GetGameCardDto> GetGameForUserProfile(Long userId) {

        //This Method returns a List of GetGameCardDto for the User (with "USER" role) page
        return gameRepository.findByFollows_UserWhoFollowed_IdAndFollows_IsFollowedTrue(userId)
                .stream()
                .map(GetGameCardDto::new)
                .collect(Collectors.toList());
    }

    public List<GetGamesForDeveloperDto> GetGamesByDeveloper(Long devId) {

        //This Method returns a List of GetGamesForDeveloperDto for the User (with "DEVELOPER" role) page
        return gameRepository.findByOwnerId(devId).stream()
                .map(GetGamesForDeveloperDto::new)
                .collect(Collectors.toList());
    }

    public void Create(GameForCreationAndUpdateDto newGame, Long userId) {
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
        gameToCreate.setReviewAmount(0);
        gameToCreate.setTotalRating(0);

        gameRepository.save(gameToCreate);
    }

    public void Update(GameForCreationAndUpdateDto gameToUpdate, Long gameId) {
        Optional<Game> currentGame = gameRepository.findById(gameId);

        List<Tag> tagsForUpdate = new ArrayList<>();

        for (Long tagId : gameToUpdate.getTagsId()) {
            tagsForUpdate.add(tagRepository.findById(tagId).get());
        }

        if(currentGame.isPresent()){
            Game game = currentGame.get();
            game.setName(gameToUpdate.getName());
            game.setProfileImage(gameToUpdate.getProfileImage());
            game.setBackgroundImage(gameToUpdate.getBackgroundImage());
            game.setDescription(gameToUpdate.getDescription());
            game.setTags(tagsForUpdate);
            gameRepository.save(game);
        }
    }

    @Transactional
    public void UpdateRating(Game game, ReviewPostForCreationAndUpdateDto review) {

        //Updates rating when a ReviewPost is created
        gameRepository.updateRating(game.getId(), review.getRating());
    }

    public void UpdateRatingWhenUpdateReview(Game game, ReviewPostForCreationAndUpdateDto review, float oldRating) {

        //Updates rating when a ReviewPost is edited
        float newRating = review.getRating();
        game.setTotalRating(game.getTotalRating() - oldRating);
        game.setTotalRating(game.getTotalRating() + newRating);

        game.setRating(game.getTotalRating() / game.getReviewAmount());

        gameRepository.save(game);
    }

    public void UpdateRatingWhenDeleteReview(Game game, ReviewPost reviewToDelete) {

        //Updates rating when a ReviewPost is deleted
        float rating = reviewToDelete.getRating();
        game.setTotalRating(game.getTotalRating() - rating);

        game.setReviewAmount(game.getReviewAmount() - 1);

        game.setRating(game.getTotalRating() / game.getReviewAmount());

        gameRepository.save(game);
    }

    public void Delete(Long id, Long userDevId) {
        Optional<Game> currentGame = gameRepository.findById(id);
        Game game = null;

        if(currentGame.isPresent()) {
            game = currentGame.get();

            //Checking that the current User is the owner of the Game
            if (Objects.equals(game.getOwner().getId(), userDevId)){
                gameRepository.deleteById(id);
            }
        }
    }

    public byte[] exportPdf() throws JRException, FileNotFoundException {
        return gameReportGenerator.exportToPdf(gameRepository.findAll());
    }
}
