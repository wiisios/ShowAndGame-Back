package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto.GetLikeDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto.LikeForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserLike;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserLikeRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserLikeService {
    private final UserLikeRepository userLikeRepository;
    private final UserRepository userRepository;
    private final FeedPostRepository feedPostRepository;

    @Autowired
    public UserLikeService(UserLikeRepository userLikeRepository, UserRepository userRepository, FeedPostRepository feedPostRepository) {
        this.userLikeRepository = userLikeRepository;
        this.userRepository = userRepository;
        this.feedPostRepository = feedPostRepository;
    }

    public UserLike GetById(Long id) {
        Optional<UserLike> like = userLikeRepository.findById(id);

        return like.orElse(null);
    }

    public List<UserLike> GetAll() {
        return userLikeRepository.findAll();
    }

    public void Create (LikeForCreationDto likeToCreate) {
        UserLike newUserLike = new UserLike();
        User userWhoLiked = userRepository.findById(likeToCreate.getUserId()).get();
        FeedPost likedPost = feedPostRepository.findById(likeToCreate.getFeedPostId()).get();

        newUserLike.setUserWhoLiked(userWhoLiked);
        newUserLike.setFeedPostLiked(likedPost);
        newUserLike.setLiked(true);

        userLikeRepository.save(newUserLike);
    }

    public void Update(GetLikeDto like) {
        Optional<UserLike> currentLike = userLikeRepository.findById(like.getId());

        if (currentLike.isPresent()) {
            UserLike userLikeToUpdate = currentLike.get();
            userLikeToUpdate.setLiked(like.isLiked());
            userLikeRepository.save(userLikeToUpdate);
        }
    }

    @Transactional
    public String toggleLike(Long userId, Long feedPostId) {
        Optional<UserLike> likeOpt = userLikeRepository.findLikeByUserIdAndFeedPostId(userId, feedPostId);
        FeedPost currentPost = feedPostRepository.findById(feedPostId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (likeOpt.isPresent()) {
            UserLike existingLike = likeOpt.get();
            boolean isLiked = existingLike.isLiked();
            existingLike.setLiked(!isLiked);
            userLikeRepository.save(existingLike);

            if (isLiked) {
                currentPost.setLikesCounter(currentPost.getLikesCounter() - 1);
                feedPostRepository.save(currentPost);
                return "Like removed";
            } else {
                currentPost.setLikesCounter(currentPost.getLikesCounter() + 1);
                feedPostRepository.save(currentPost);
                return "Like added";
            }
        } else {
            LikeForCreationDto newLike = new LikeForCreationDto(userId, feedPostId);
            Create(newLike);

            currentPost.setLikesCounter(currentPost.getLikesCounter() + 1);
            feedPostRepository.save(currentPost);
            return "Like added";
        }
    }

    public void Delete(Long id) {
        Optional<UserLike> currentLike = userLikeRepository.findById(id);

        if(currentLike.isPresent()) {
            UserLike userLike = currentLike.get();
            userLikeRepository.delete(userLike);
        }
    }
}
