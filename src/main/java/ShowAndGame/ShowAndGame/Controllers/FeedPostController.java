package ShowAndGame.ShowAndGame.Controllers;


import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.FeedPostForCreationdDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostForUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Services.FeedPostService;
import ShowAndGame.ShowAndGame.Services.UserLikeService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "/feedPosts")
public class FeedPostController {
    @Autowired
    private FeedPostService feedPostService;

    @Autowired
    private UserLikeService userLikeService;

    @GetMapping()
    public ResponseEntity<List<GetFeedPostDto>> getAllPosts(@AuthenticationPrincipal User currentUser) {
        return  ResponseEntity.ok(feedPostService.GetAll(currentUser.getId()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetFeedPostDto> getPost(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(feedPostService.GetById(postId, currentUser.getId()));
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<GetFeedPostDto>> getPostByGameId(@PathVariable Long gameId, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(feedPostService.GetFeedPostsByGameId(gameId, currentUser.getId()));
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<String> createPost(@RequestBody FeedPostForCreationdDto feedPost, @PathVariable Long gameId, @AuthenticationPrincipal User currentUser) {
        ResponseEntity<String> response = null;
        Long userId = currentUser.getId();

        if (gameId == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        } else if (userId == null) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            feedPostService.Create(feedPost, userId, gameId);
            response = ResponseEntity.ok().body("Post created");
        }

        return response;
    }

    @PutMapping("/{postId}")
    public ResponseEntity<GetFeedPostForUpdateDto> updatePost(@RequestBody GetFeedPostForUpdateDto feedPost, @PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
        ResponseEntity<GetFeedPostForUpdateDto> response = null;
        Long userId = currentUser.getId();

        if (postId != null && feedPostService.GetById(postId, userId) != null) {
            feedPostService.Update(feedPost, userId, postId);
            response = ResponseEntity.ok(feedPost);}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        ResponseEntity<String> response = null;
        Long userId = currentUser.getId();

        if (feedPostService.GetById(id, userId) != null) {
            feedPostService.Delete(id, userId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<String> Like(@PathVariable Long postId, @AuthenticationPrincipal User currentUser) {
        Long userId = currentUser.getId();
        String response = userLikeService.toggleLike(userId, postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportPdf() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("postsReport", "feedPostsReport.pdf");

        return ResponseEntity.ok().headers(headers).body(feedPostService.exportPdf());
    }
}


