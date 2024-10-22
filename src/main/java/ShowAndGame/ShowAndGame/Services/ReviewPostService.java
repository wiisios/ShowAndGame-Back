package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.GetReviewPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.ReviewPostForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
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
    public ReviewPostService(ReviewPostRepository reviewPostRepository, GameRepository gameRepository, UserRepository userRepository, GameService gameService){
        this.reviewPostRepository = reviewPostRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.gameService = gameService;
    }

    public void Create(ReviewPostForCreationAndUpdateDto newReviewPost, Long gameId, Long userId) {
        ReviewPost reviewPostToCreate = new ReviewPost();
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

        reviewPostToCreate.setDescription(newReviewPost.getDescription());
        reviewPostToCreate.setDate(dateNow);
        reviewPostToCreate.setRating(newReviewPost.getRating());
        reviewPostToCreate.setGame(currentGame);
        reviewPostToCreate.setUser(currentUser);

        gameService.UpdateRating(currentGame, newReviewPost);

        reviewPostRepository.save(reviewPostToCreate);
    }

    public void Delete(Long id, Long userId) {
        Optional<ReviewPost> currentReviewPost = reviewPostRepository.findById(id);

        if(currentReviewPost.isPresent()){
            ReviewPost reviewPost = currentReviewPost.get();
            if (Objects.equals(reviewPost.getUser().getId(), userId)){
                reviewPostRepository.deleteById(id);
            }
        }
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

    public List<GetReviewPostDto> GetReviewPostsByGameId(Long gameId){
        List<ReviewPost> allReviewPosts =  reviewPostRepository.findByGameId(gameId);

        return allReviewPosts.stream()
                .map(GetReviewPostDto::new)
                .collect(Collectors.toList());
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
            if (Objects.equals(reviewPost.getUser().getId(), userId)) {
                reviewPost.setDescription(reviewPostToUpdate.getDescription());
                reviewPost.setRating(reviewPostToUpdate.getRating());

                gameService.UpdateRatingWhenUpdateReview(currentGame, reviewPostToUpdate);
                reviewPostRepository.save(reviewPost);
            }
        }
    }
}
