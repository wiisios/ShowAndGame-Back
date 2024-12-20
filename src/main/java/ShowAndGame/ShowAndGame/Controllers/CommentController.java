package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto.CommentForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto.GetCommentForPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto.GetCommentForUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping()
    public ResponseEntity<List<GetCommentForPostDto>> getAllComments() {
        return  ResponseEntity.ok(commentService.GetAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCommentForPostDto> getComment(@PathVariable Long id) {
        GetCommentForPostDto comment = commentService.GetById(id).orElse((null));

        return ResponseEntity.ok(comment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<GetCommentForPostDto>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.GetCommentsByPostId(postId));
    }

    @PostMapping()
    public ResponseEntity<String> createComment(@RequestBody CommentForCreationAndUpdateDto comment, Long postId, @AuthenticationPrincipal User currentUser) {
        ResponseEntity<String> response = null;
        Long userId = currentUser.getId();

        if (userId == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            commentService.Create(comment, postId, userId);
            response = ResponseEntity.ok().body("Comment created");
        }

        return response;
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<GetCommentForUpdateDto> updateComment(@RequestBody GetCommentForUpdateDto commentToUpdate, @PathVariable Long commentId, @AuthenticationPrincipal User currentUser) {
        ResponseEntity<GetCommentForUpdateDto> response = null;
        Long userId = currentUser.getId();

        if (commentId != null && commentService.GetById(commentId).isPresent()) {
            commentService.Update(commentToUpdate, userId, commentId);
            response = ResponseEntity.status(HttpStatus.OK).body(commentToUpdate);
        }
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        ResponseEntity<String> response = null;
        Long userId = currentUser.getId();

        if (commentService.GetById(id).isPresent()) {
            commentService.Delete(id, userId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");
        }
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
