package ShowAndGame.ShowAndGame.Controllers;


import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostForCreationdDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetFeedPostDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Services.FeedPostService;
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

    @GetMapping()
    public ResponseEntity<List<GetFeedPostDto>> GetAllPosts() {return  ResponseEntity.ok(feedPostService.GetAll());}

    @GetMapping("/{id}")
    public ResponseEntity<GetFeedPostDto> GetPost(@PathVariable Long id) {

        GetFeedPostDto feedPost = feedPostService.GetById(id).orElse((null));

        return ResponseEntity.ok(feedPost);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<GetFeedPostDto>> GetPostByGameId(@PathVariable Long gameId){
        return ResponseEntity.ok(feedPostService.GetFeedPostsByGameId(gameId));
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<String> CreatePost(@RequestBody FeedPostForCreationdDto feedPost, @PathVariable Long gameId ){
        ResponseEntity<String> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();


        if (gameId == null){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        } else if (userId == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            feedPostService.Create(feedPost, gameId, userId);
            response = ResponseEntity.ok().body("Post created");
        }

        return response;
    }

    @PutMapping()
    public ResponseEntity<FeedPost> UpdatePost(@RequestBody FeedPost feedPost){
        ResponseEntity<FeedPost> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();

        if (feedPost.getId() != null && feedPostService.GetById(feedPost.getId()).isPresent()){
            feedPostService.Update(feedPost, userId);
            response = ResponseEntity.ok(feedPost);}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeletePost(@PathVariable Long id){
        ResponseEntity<String> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();

        if (feedPostService.GetById(id).isPresent()){
            feedPostService.Delete(id, userId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    
}


