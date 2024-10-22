package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.FollowDto.FollowForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FollowDto.GetFollowDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Follow;
import ShowAndGame.ShowAndGame.Services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("/all")
    public ResponseEntity<List<Follow>> GetAllUserLikes() {
        return ResponseEntity.ok(followService.GetAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Follow> GetUserLike(@PathVariable Long id) {
        return ResponseEntity.ok(followService.GetById(id));
    }

    @PostMapping()
    public ResponseEntity<String> CreateLike(@RequestBody FollowForCreationDto newFollow){
        followService.Create(newFollow);
        return ResponseEntity.ok().body("Like created");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GetFollowDto> UpdateLike(@RequestBody GetFollowDto followToUpdate, @PathVariable Long id){

        if (id != null && followService.GetById(id) != null){
            followService.Update(followToUpdate);
            return ResponseEntity.ok(followToUpdate);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteLike(@PathVariable Long id) {

        if (followService.GetById(id) != null) {
            followService.Delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
