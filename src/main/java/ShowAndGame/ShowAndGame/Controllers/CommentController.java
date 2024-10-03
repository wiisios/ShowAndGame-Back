package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.CommentForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetCommentForPostDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Comment;
import ShowAndGame.ShowAndGame.Services.CommentService;
import ShowAndGame.ShowAndGame.Util.CurrentUserUtil;
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

    @Autowired
    private CurrentUserUtil currentUserUtil;


    @GetMapping()
    public ResponseEntity<List<GetCommentForPostDto>> GetAllComments() {return  ResponseEntity.ok(commentService.GetAll());}

    @GetMapping("/{id}")
    public ResponseEntity<GetCommentForPostDto> GetComment(@PathVariable Long id) {

        GetCommentForPostDto comment = commentService.GetById(id).orElse((null));

        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<GetCommentForPostDto>> GetCommentsByPost(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.GetCommentsByPostId(postId));
    }


    @PostMapping()
        public ResponseEntity<String> CreateComment(@RequestBody CommentForCreationAndUpdateDto comment, Long postId){
        ResponseEntity<String> response = null;

        Long userId = currentUserUtil.GetCurrentUserId();

        if (userId == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {

            commentService.Create(comment, postId, userId);
            response = ResponseEntity.ok().body("Comment created");
        }

        return response;
    }

    @PutMapping()
    public ResponseEntity<GetCommentForPostDto> UpdateComment(@RequestBody GetCommentForPostDto commentToUpdate){
        ResponseEntity<GetCommentForPostDto> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();

        if (commentToUpdate.getId() != null && commentService.GetById(commentToUpdate.getId()).isPresent()){
            commentService.Update(commentToUpdate, userId);
            response = ResponseEntity.status(HttpStatus.OK).body(commentToUpdate);
        }
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteComment(@PathVariable Long id){
        ResponseEntity<String> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();

        if (commentService.GetById(id).isPresent()){
            commentService.Delete(id, userId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
