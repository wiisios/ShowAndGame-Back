package ShowAndGame.ShowAndGame.Controllers;


import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Services.FeedPostService;
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

    @GetMapping()
    public ResponseEntity<List<FeedPost>> getAllPosts() {return  ResponseEntity.ok(feedPostService.GetAll());}

    @GetMapping("/{id}")
    public ResponseEntity<FeedPost> getPost(@PathVariable Long id) {

        FeedPost feedPost = feedPostService.GetById(id).orElse((null));

        return ResponseEntity.ok(feedPost);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<FeedPost>> getPostByGameId(@PathVariable Long gameId){
        return ResponseEntity.ok(feedPostService.GetFeedPostsByGameId(gameId));
    }

    @PostMapping()
    public ResponseEntity<FeedPost> createPost(@RequestBody FeedPost feedPost){

        return ResponseEntity.ok(feedPostService.Create(feedPost));
    }

    @PutMapping()
    public ResponseEntity<FeedPost> updatePost(@RequestBody FeedPost feedPost){
        ResponseEntity<FeedPost> response = null;

        if (feedPost.getId() != null && feedPostService.GetById(feedPost.getId()).isPresent())
            response = ResponseEntity.ok(feedPostService.Update(feedPost));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (feedPostService.GetById(id).isPresent()){
            feedPostService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    
}


