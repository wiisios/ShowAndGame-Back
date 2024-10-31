package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.FeedPostForCreationdDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostForReportDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostForUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserLikeRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import ShowAndGame.ShowAndGame.Util.PostReportGenerator;

import jakarta.persistence.EntityNotFoundException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedPostService {
    private final FeedPostRepository feedPostRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final UserLikeService userLikeService;
    private final UserLikeRepository userLikeRepository;

    @Autowired
    private PostReportGenerator postReportGenerator;

    @Autowired
    public FeedPostService(FeedPostRepository feedPostRepository, GameRepository gameRepository, UserRepository userRepository, UserLikeService userLikeService, UserLikeRepository userLikeRepository) {
        this.feedPostRepository = feedPostRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.userLikeService = userLikeService;
        this.userLikeRepository = userLikeRepository;
    }

    public GetFeedPostDto GetById(Long id, Long userId) {
        return feedPostRepository.findByIdWithLikes(id, userId);
    }

    public List<GetFeedPostDto> GetAll(Long userId) {
        return feedPostRepository.findAllWithLikes(userId);
    }

    public List<GetFeedPostDto> GetFeedPostsByGameId(Long gameId, Long userId) {

        //This returns a List of GetFeedPostDto when a User selects a specific game on the feed
        return feedPostRepository.findAllByGameIdWithLikes(gameId, userId);
    }

    public void Create(FeedPostForCreationdDto newFeedPost, Long userId, Long gameId) {
        FeedPost feedPostToCreate = new FeedPost();
        Optional<Game> game = gameRepository.findById(gameId);
        Optional<User> user = userRepository.findById(userId);
        LocalDate dateNow = LocalDate.now();

        if (game.isEmpty()) {
            throw new EntityNotFoundException("Game with id " + gameId + " not found");
        }
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }

        Game currentGame = game.get();
        User currentUser = user.get();

        feedPostToCreate.setDescription(newFeedPost.getDescription());
        feedPostToCreate.setImage(newFeedPost.getImage());
        feedPostToCreate.setUser(currentUser);
        feedPostToCreate.setGame(currentGame);
        feedPostToCreate.setDate(dateNow);
        feedPostToCreate.setComments(new ArrayList<Comment>());
        feedPostToCreate.setLikesCounter(0);

        feedPostRepository.save(feedPostToCreate);
    }

    public void Update(GetFeedPostForUpdateDto feedPostDto, Long userId, Long feedPostId) {
        Optional<FeedPost> currentFeedPost = feedPostRepository.findById(feedPostId);
        FeedPost feedPost = null;

        if(currentFeedPost.isPresent()) {
            feedPost = currentFeedPost.get();

            //Checking that the current User is the same as the one who created the FeedPost
            if (Objects.equals(feedPost.getUser().getId(), userId)) {
                feedPost.setDescription(feedPostDto.getDescription());
                feedPost.setImage(feedPostDto.getImage());
                feedPostRepository.save(feedPost);
            }
        }
    }

    public void UpdateLikesAmount(Long userId, Long feedPostId) {
        UserLike like = userLikeRepository.findLikeByUserIdAndFeedPostId(userId, feedPostId).get();
        FeedPost currentPost = feedPostRepository.findById(feedPostId).get();

        //Checking the like value (True or False) to update the amount of likes of the FeedPost
        if(like.isLiked()){
            currentPost.setLikesCounter(currentPost.getLikesCounter()+1);
            feedPostRepository.save(currentPost);
        }
        else{
            currentPost.setLikesCounter(currentPost.getLikesCounter()-1);
            feedPostRepository.save(currentPost);
        }
    }

    public void Delete(Long id, Long userId) {
        Optional<FeedPost> currentFeedPost = feedPostRepository.findById(id);

        if(currentFeedPost.isPresent()){
            FeedPost feedPost = currentFeedPost.get();

            //Checking that the current User is the same as the one who created the FeedPost
            if (Objects.equals(feedPost.getUser().getId(), userId)){
                feedPostRepository.deleteById(id);
            }
        }
    }

    public byte[] exportPdf() throws JRException, FileNotFoundException {
        return postReportGenerator.exportToPdf(feedPostRepository.findAll()
                .stream().map(GetFeedPostForReportDto::new)
                .collect(Collectors.toList()));
    }
}
