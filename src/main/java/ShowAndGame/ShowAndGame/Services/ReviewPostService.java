package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.GetReviewPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.ReviewPostForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.ReviewPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewPostService {
    private final ReviewPostRepository reviewPostRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameService gameService;

    @Autowired
    public ReviewPostService(ReviewPostRepository reviewPostRepository, GameRepository gameRepository, UserRepository userRepository, GameService gameService) {
        this.reviewPostRepository = reviewPostRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.gameService = gameService;
    }

    public GetReviewPostDto GetById(Long postId) {
        Optional<ReviewPost> reviewPost = reviewPostRepository.findById(postId);

        if (reviewPost.isEmpty()){
            throw new EntityNotFoundException("Review with id " + postId + " not found");
        }

        ReviewPost currentReview = reviewPost.get();

        return new GetReviewPostDto(currentReview);
    }

    public List<GetReviewPostDto> GetAll() {
        return reviewPostRepository.findAll().stream()
                .map(GetReviewPostDto::new)
                .collect(Collectors.toList());
    }

    public List<GetReviewPostDto> GetReviewPostsByGameId(Long gameId) {
        List<ReviewPost> allReviewPosts =  reviewPostRepository.findByGameId(gameId);

        return allReviewPosts.stream()
                .map(GetReviewPostDto::new)
                .collect(Collectors.toList());
    }

    public void Create(ReviewPostForCreationAndUpdateDto newReviewPost, Long gameId, Long userId) {
        Optional<Game> gameOpt = gameRepository.findById(gameId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (gameOpt.isEmpty() || userOpt.isEmpty()) {
            throw new EntityNotFoundException("Game or User not found");
        }

        Game currentGame = gameOpt.get();
        User currentUser = userOpt.get();

        ReviewPost reviewPostToCreate = new ReviewPost();
        reviewPostToCreate.setDescription(newReviewPost.getDescription());
        reviewPostToCreate.setDate(LocalDate.now());
        reviewPostToCreate.setRating(newReviewPost.getRating());
        reviewPostToCreate.setGame(currentGame);
        reviewPostToCreate.setUser(currentUser);

        gameService.UpdateRating(currentGame, newReviewPost);
        reviewPostRepository.save(reviewPostToCreate);
    }

    public void Update(ReviewPostForCreationAndUpdateDto reviewPostToUpdate, Long reviewPostId, Long gameId, Long userId) {
        Optional<ReviewPost> currentPost = reviewPostRepository.findById(reviewPostId);
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isEmpty()) {
            throw new EntityNotFoundException("Game with id " + gameId + " not found");
        }

        Game currentGame = game.get();

        if(currentPost.isPresent()){
            ReviewPost reviewPost = currentPost.get();

            //Checking that the current User is the same as the one who created the ReviewPost
            if (Objects.equals(reviewPost.getUser().getId(), userId)) {
                float oldRating = reviewPost.getRating();
                reviewPost.setDescription(reviewPostToUpdate.getDescription());
                reviewPost.setRating(reviewPostToUpdate.getRating());

                //Update rating
                gameService.UpdateRatingWhenUpdateReview(currentGame, reviewPostToUpdate, oldRating);
                reviewPostRepository.save(reviewPost);
            }
        }
    }

    public void Delete(Long id, Long userId) {
        Optional<ReviewPost> currentReviewPost = reviewPostRepository.findById(id);

        if(currentReviewPost.isPresent()){
            ReviewPost reviewPost = currentReviewPost.get();

            //Checking that the current User is the same as the one who created the ReviewPost
            if (Objects.equals(reviewPost.getUser().getId(), userId)){
                Game gameToUpdate = reviewPostRepository.findGameByReviewPostId(id);

                //Update rating
                gameService.UpdateRatingWhenDeleteReview(gameToUpdate, reviewPost);
                reviewPostRepository.deleteById(id);
            }
        }
    }
}
