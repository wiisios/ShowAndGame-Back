package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto.GetLikeDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto.LikeForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.Like;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.LikeRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final FeedPostRepository feedPostRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserRepository userRepository, FeedPostRepository feedPostRepository){
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.feedPostRepository = feedPostRepository;
    }

    public void Create (LikeForCreationDto likeToCreate){
        Like newLike = new Like();
        User userWhoLiked = userRepository.findById(likeToCreate.getUserId()).get();
        FeedPost likedPost = feedPostRepository.findById(likeToCreate.getFeedPostId()).get();

        newLike.setUserWhoLiked(userWhoLiked);
        newLike.setFeedPostLiked(likedPost);
        newLike.setLiked(true);

        likeRepository.save(newLike);
    }

    public Like GetLikeById(Long id){
        Optional<Like> like = likeRepository.findById(id);
        return like.orElse(null);
    }

    public List<Like> GetAll(){
        return likeRepository.findAll();
    }

    public void Update(GetLikeDto like){
        Optional<Like> currentLike = likeRepository.findById(like.getId());

        if (currentLike.isPresent()){
            Like likeToUpdate = currentLike.get();
            likeToUpdate.setLiked(like.isLiked());
            likeRepository.save(likeToUpdate);
        }
    }

    public void Delete(Long id){
        Optional<Like> currentLike = likeRepository.findById(id);

        if(currentLike.isPresent()){
            Like like = currentLike.get();
            likeRepository.delete(like);
        }

    }

    public boolean isLikedCheck(Long userId, Long feedPostId) {
        Optional<Like> like = likeRepository.findLikeByUserIdAndFeedPostId(userId, feedPostId);
        return like.map(Like::isLiked).orElse(false);
    }

    public void toggleLike(Long userId, Long feedPostId) {
        Optional<Like> like = likeRepository.findLikeByUserIdAndFeedPostId(userId, feedPostId);

        if (like.isPresent()) {
            Like existingLike = like.get();
            existingLike.setLiked(!existingLike.isLiked());
            likeRepository.save(existingLike);
        } else {
            LikeForCreationDto newLike = new LikeForCreationDto(userId, feedPostId);
            Create(newLike);
        }
    }
}
