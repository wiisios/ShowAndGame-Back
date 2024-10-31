package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.GetReviewPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto.ReviewPostForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Services.ReviewPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reviewPosts")
public class ReviewPostController {
    @Autowired
    private ReviewPostService reviewPostService;

    @GetMapping()
    public ResponseEntity<List<GetReviewPostDto>> GetAllPosts() {
        return ResponseEntity.ok(reviewPostService.GetAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetReviewPostDto> GetPost(@PathVariable Long id) {
        GetReviewPostDto reviewPost = reviewPostService.GetById(id);

        if (reviewPost != null) {
            return ResponseEntity.ok(reviewPost);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/gamePosts/{gameId}")
    public ResponseEntity<List<GetReviewPostDto>> GetReviewsPostsByGameId(@PathVariable Long gameId) {
        return ResponseEntity.ok(reviewPostService.GetReviewPostsByGameId(gameId));
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<String> CreatePost(@RequestBody ReviewPostForCreationAndUpdateDto reviewPost, @PathVariable Long gameId, @AuthenticationPrincipal User currentUser) {
        Long userId = currentUser.getId();

        reviewPostService.Create(reviewPost, gameId, userId);

        return ResponseEntity.ok("Post created successfully");
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<ReviewPostForCreationAndUpdateDto> UpdateReviewPost(@RequestBody ReviewPostForCreationAndUpdateDto reviewPostToUpdate, Long id, @PathVariable Long gameId, @AuthenticationPrincipal User currentUser) {
        ResponseEntity<ReviewPostForCreationAndUpdateDto> response = null;
        Long userId = currentUser.getId();

        if (id != null && reviewPostService.GetById(id) != null) {
            reviewPostService.Update(reviewPostToUpdate, id,gameId, userId);
            response = ResponseEntity.status(HttpStatus.OK).body(reviewPostToUpdate);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeletePost(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        ResponseEntity<String> response = null;
        Long userId = currentUser.getId();

        if (reviewPostService.GetById(id) != null) {
            reviewPostService.Delete(id, userId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
        }
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
