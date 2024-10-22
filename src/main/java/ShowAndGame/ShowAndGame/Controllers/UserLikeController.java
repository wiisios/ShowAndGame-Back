package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto.GetLikeDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.LikeDto.LikeForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserLike;
import ShowAndGame.ShowAndGame.Services.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userlikes")
public class UserLikeController {

    @Autowired
    private UserLikeService userLikeService;

    @GetMapping("/all")
    public ResponseEntity<List<UserLike>> GetAllUserLikes() {
        return ResponseEntity.ok(userLikeService.GetAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLike> GetUserLike(@PathVariable Long id) {
        return ResponseEntity.ok(userLikeService.GetById(id));
    }

    @PostMapping()
    public ResponseEntity<String> CreateLike(@RequestBody LikeForCreationDto newLike){
        userLikeService.Create(newLike);
        return ResponseEntity.ok().body("Like created");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GetLikeDto> UpdateLike(@RequestBody GetLikeDto likeToUpdate, @PathVariable Long id){

        if (id != null && userLikeService.GetById(id) != null){
            userLikeService.Update(likeToUpdate);
            return ResponseEntity.ok(likeToUpdate);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteLike(@PathVariable Long id) {

        if (userLikeService.GetById(id) != null) {
            userLikeService.Delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
