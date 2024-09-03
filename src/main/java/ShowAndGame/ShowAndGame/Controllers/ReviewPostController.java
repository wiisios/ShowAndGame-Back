package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import ShowAndGame.ShowAndGame.Services.ReviewPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reviewPosts")
public class ReviewPostController {

    @Autowired
    private ReviewPostService reviewPostService;

    @GetMapping()
    public ResponseEntity<List<ReviewPost>> getAllPosts() {return  ResponseEntity.ok(reviewPostService.searchAll());}

    @GetMapping("/{id}")
    public ResponseEntity<ReviewPost> getPost(@PathVariable Long id) {

        ReviewPost reviewPost = reviewPostService.search(id).orElse((null));

        return ResponseEntity.ok(reviewPost);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<List<ReviewPost>> getReviewsPostsByGameId(@PathVariable Long gameId){
        return ResponseEntity.ok(reviewPostService.searchReviewPostsByGameId(gameId));
    }

    @PostMapping()
    public ResponseEntity<ReviewPost> createPost(@RequestBody ReviewPost reviewPost){

        return ResponseEntity.ok(reviewPostService.create(reviewPost));
    }

    @PutMapping()
    public ResponseEntity<ReviewPost> updatePost(@RequestBody ReviewPost reviewPost){
        ResponseEntity<ReviewPost> response = null;

        if (reviewPost.getId() != null && reviewPostService.search(reviewPost.getId()).isPresent())
            response = ResponseEntity.ok(reviewPostService.update(reviewPost));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (reviewPostService.search(id).isPresent()){
            reviewPostService.delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


}
