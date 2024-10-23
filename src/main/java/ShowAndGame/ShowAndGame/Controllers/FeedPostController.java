package ShowAndGame.ShowAndGame.Controllers;


import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.FeedPostForCreationdDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostForUpdateDto;
import ShowAndGame.ShowAndGame.Services.FeedPostService;
import ShowAndGame.ShowAndGame.Services.UserLikeService;
import ShowAndGame.ShowAndGame.Util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/feedPosts")
public class FeedPostController {

    @Autowired
    private FeedPostService feedPostService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private UserLikeService userLikeService;

    @GetMapping()
    public ResponseEntity<List<GetFeedPostDto>> getAllPosts() {
        Long userId = currentUserUtil.GetCurrentUserId();
        return  ResponseEntity.ok(feedPostService.GetAll(userId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetFeedPostDto> getPost(@PathVariable Long postId) {
        Long userId = currentUserUtil.GetCurrentUserId();
        GetFeedPostDto feedPost = feedPostService.GetById(postId, userId).orElse((null));

        return ResponseEntity.ok(feedPost);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<GetFeedPostDto>> getPostByGameId(@PathVariable Long gameId){
        Long userId = currentUserUtil.GetCurrentUserId();
        return ResponseEntity.ok(feedPostService.GetFeedPostsByGameId(gameId, userId));
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<String> createPost(@RequestBody FeedPostForCreationdDto feedPost, @PathVariable Long gameId ){
        ResponseEntity<String> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();


        if (gameId == null){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        } else if (userId == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            feedPostService.Create(feedPost, userId, gameId);
            response = ResponseEntity.ok().body("Post created");
        }

        return response;
    }

    @PutMapping("/{postId}")
    public ResponseEntity<GetFeedPostForUpdateDto> updatePost(@RequestBody GetFeedPostForUpdateDto feedPost, @PathVariable Long postId){
        ResponseEntity<GetFeedPostForUpdateDto> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();

        if (postId != null && feedPostService.GetById(postId, userId).isPresent()){
            feedPostService.Update(feedPost, userId, postId);
            response = ResponseEntity.ok(feedPost);}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        ResponseEntity<String> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();

        if (feedPostService.GetById(id, userId).isPresent()){
            feedPostService.Delete(id, userId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<String> Like(@PathVariable Long postId){
        Long userId = currentUserUtil.GetCurrentUserId();

        userLikeService.toggleLike(userId, postId);
        feedPostService.UpdateLikesAmount(userId, postId);

        boolean isLiked = userLikeService.isLikedCheck(userId, postId);

        String response = isLiked ? "Like added" : "Like removed";

        return ResponseEntity.ok(response);
    }
}


