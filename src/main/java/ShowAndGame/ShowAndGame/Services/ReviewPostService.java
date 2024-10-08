package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.GetReviewPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.GetReviewPostForUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.ReviewPostForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.ReviewPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
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

    @Autowired
    public ReviewPostService(ReviewPostRepository reviewPostRepository, GameRepository gameRepository, UserRepository userRepository){
        this.reviewPostRepository = reviewPostRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public void Create(ReviewPostForCreationAndUpdateDto newReviewPost, Long gameId, Long userId) {

        ReviewPost reviewPostToCreate = new ReviewPost();
        Optional<Game> game = gameRepository.findById(gameId);
        Optional<User> user = userRepository.findById(userId);
        Game currentGame = null;
        User currentUser = null;
        LocalDate dateNow = LocalDate.now();

        if (game.isPresent()) {
            currentGame = game.get();
        }
        if (user.isPresent()){
            currentUser = user.get();
        }

        reviewPostToCreate.setDescription(newReviewPost.getDescription());
        reviewPostToCreate.setDate(dateNow);
        reviewPostToCreate.setRating(newReviewPost.getRating());
        reviewPostToCreate.setGame(currentGame);
        reviewPostToCreate.setUser(currentUser);

        reviewPostRepository.save(reviewPostToCreate);
    }

    public void Delete(Long id, Long userId) {
        Optional<ReviewPost> currentReviewPost = reviewPostRepository.findById(id);
        ReviewPost reviewPost = null;

        if(currentReviewPost.isPresent()){
            reviewPost = currentReviewPost.get();
            if (Objects.equals(reviewPost.getUser().getId(), userId)){
                reviewPostRepository.deleteById(id);
            }
        }
    }

    public GetReviewPostDto GetById(Long id) {
        Optional<ReviewPost> reviewPost = reviewPostRepository.findById(id);
        User user = null;
        ReviewPost currentReview = null;

        if (reviewPost.isPresent()){
            currentReview = reviewPost.get();
            user = currentReview.getUser();
        }

        return new GetReviewPostDto(currentReview, user);
    }

    public List<GetReviewPostDto> GetAll() {
        List<ReviewPost> allReviewPosts =  reviewPostRepository.findAll();
        return allReviewPosts.stream()
                .map(reviewPost -> new GetReviewPostDto(reviewPost, reviewPost.getUser()))
                .collect(Collectors.toList());
    }

    public List<GetReviewPostDto> GetReviewPostsByGameId(Long gameId){
        List<ReviewPost> allReviewPosts =  reviewPostRepository.findByGameId(gameId);
        return allReviewPosts.stream()
                .map(reviewPost -> new GetReviewPostDto(reviewPost, reviewPost.getUser()))
                .collect(Collectors.toList());
    }

    public void Update(GetReviewPostForUpdateDto reviewPostToUpdate, Long reviewPostId) {
        Optional<ReviewPost> currentPost = reviewPostRepository.findById(reviewPostId);
        ReviewPost reviewPost = null;

        if(currentPost.isPresent()){
            reviewPost = currentPost.get();
            reviewPost.setDescription(reviewPostToUpdate.getDescription());
            reviewPost.setRating(reviewPostToUpdate.getRating());
            reviewPostRepository.save(reviewPost);
        }
    }
}
