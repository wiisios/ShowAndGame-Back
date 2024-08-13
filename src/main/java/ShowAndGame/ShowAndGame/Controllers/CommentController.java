package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Entities.Comment;
import ShowAndGame.ShowAndGame.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping()
    public ResponseEntity<List<Comment>> getAllComments() {return  ResponseEntity.ok(commentService.searchAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {

        Comment comment = commentService.search(id).orElse((null));

        return ResponseEntity.ok(comment);
    }


    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){

        return ResponseEntity.ok(commentService.create(comment));
    }

    @PutMapping()
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment){
        ResponseEntity<Comment> response = null;

        if (comment.getId() != null && commentService.search(comment.getId()).isPresent())
            response = ResponseEntity.ok(commentService.update(comment));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (commentService.search(id).isPresent()){
            commentService.delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
