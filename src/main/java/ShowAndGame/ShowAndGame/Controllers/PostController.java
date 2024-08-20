package ShowAndGame.ShowAndGame.Controllers;


import ShowAndGame.ShowAndGame.Persistence.Entities.Post;
import ShowAndGame.ShowAndGame.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping()
    public ResponseEntity<List<Post>> getAllPosts() {return  ResponseEntity.ok(postService.searchAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {

        Post post = postService.search(id).orElse((null));

        return ResponseEntity.ok(post);
    }


    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody Post post){

        return ResponseEntity.ok(postService.create(post));
    }

    @PutMapping()
    public ResponseEntity<Post> updatePost(@RequestBody Post post){
        ResponseEntity<Post> response = null;

        if (post.getId() != null && postService.search(post.getId()).isPresent())
            response = ResponseEntity.ok(postService.update(post));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (postService.search(id).isPresent()){
            postService.delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
