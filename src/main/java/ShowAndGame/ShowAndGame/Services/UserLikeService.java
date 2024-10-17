package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto.GetLikeDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto.LikeForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserLike;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserLikeRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
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
    public UserLikeService(UserLikeRepository userLikeRepository, UserRepository userRepository, FeedPostRepository feedPostRepository){
        this.userLikeRepository = userLikeRepository;
        this.userRepository = userRepository;
        this.feedPostRepository = feedPostRepository;
    }

    public void Create (LikeForCreationDto likeToCreate){
        UserLike newUserLike = new UserLike();
        User userWhoLiked = userRepository.findById(likeToCreate.getUserId()).get();
        FeedPost likedPost = feedPostRepository.findById(likeToCreate.getFeedPostId()).get();

        newUserLike.setUserWhoLiked(userWhoLiked);
        newUserLike.setFeedPostLiked(likedPost);
        newUserLike.setLiked(true);

        userLikeRepository.save(newUserLike);
    }

    public UserLike GetLikeById(Long id){
        Optional<UserLike> like = userLikeRepository.findById(id);
        return like.orElse(null);
    }

    public List<UserLike> GetAll(){
        return userLikeRepository.findAll();
    }

    public void Update(GetLikeDto like){
        Optional<UserLike> currentLike = userLikeRepository.findById(like.getId());

        if (currentLike.isPresent()){
            UserLike userLikeToUpdate = currentLike.get();
            userLikeToUpdate.setLiked(like.isLiked());
            userLikeRepository.save(userLikeToUpdate);
        }
    }

    public void Delete(Long id){
        Optional<UserLike> currentLike = userLikeRepository.findById(id);

        if(currentLike.isPresent()){
            UserLike userLike = currentLike.get();
            userLikeRepository.delete(userLike);
        }

    }

    public boolean isLikedCheck(Long userId, Long feedPostId) {
        Optional<UserLike> like = userLikeRepository.findLikeByUserIdAndFeedPostId(userId, feedPostId);
        return like.map(UserLike::isLiked).orElse(false);
    }

    public void toggleLike(Long userId, Long feedPostId) {
        Optional<UserLike> like = userLikeRepository.findLikeByUserIdAndFeedPostId(userId, feedPostId);

        if (like.isPresent()) {
            UserLike existingUserLike = like.get();
            existingUserLike.setLiked(!existingUserLike.isLiked());
            userLikeRepository.save(existingUserLike);
        } else {
            LikeForCreationDto newLike = new LikeForCreationDto(userId, feedPostId);
            Create(newLike);
        }
    }
}
