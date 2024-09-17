package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Comment;
import ShowAndGame.ShowAndGame.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping()
    public ResponseEntity<List<Comment>> getAllComments() {return  ResponseEntity.ok(commentService.GetAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) {

        Comment comment = commentService.GetById(id).orElse((null));

        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId){
      return ResponseEntity.ok(commentService.GetCommentsByPostId(postId));
    }


    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){

        return ResponseEntity.ok(commentService.Create(comment));
    }

    @PutMapping()
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment){
        ResponseEntity<Comment> response = null;

        if (comment.getId() != null && commentService.GetById(comment.getId()).isPresent())
            response = ResponseEntity.ok(commentService.Update(comment));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (commentService.GetById(id).isPresent()){
            commentService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
