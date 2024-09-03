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
    public ResponseEntity<List<FeedPost>> getAllPosts() {return  ResponseEntity.ok(feedPostService.searchAll());}

    @GetMapping("/{id}")
    public ResponseEntity<FeedPost> getPost(@PathVariable Long id) {

        FeedPost feedPost = feedPostService.search(id).orElse((null));

        return ResponseEntity.ok(feedPost);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<FeedPost>> getPostByGameId(@PathVariable Long gameId){
        return ResponseEntity.ok(feedPostService.searchFeedPostsByGameId(gameId));
    }

    @PostMapping()
    public ResponseEntity<FeedPost> createPost(@RequestBody FeedPost feedPost){

        return ResponseEntity.ok(feedPostService.create(feedPost));
    }

    @PutMapping()
    public ResponseEntity<FeedPost> updatePost(@RequestBody FeedPost feedPost){
        ResponseEntity<FeedPost> response = null;

        if (feedPost.getId() != null && feedPostService.search(feedPost.getId()).isPresent())
            response = ResponseEntity.ok(feedPostService.update(feedPost));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (feedPostService.search(id).isPresent()){
            feedPostService.delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    
}


