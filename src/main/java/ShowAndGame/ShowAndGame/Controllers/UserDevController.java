package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameForFeedDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserDev;
import ShowAndGame.ShowAndGame.Services.UserDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/developers")
public class UserDevController {

    @Autowired
    private UserDevService userDevService;

    @GetMapping()
    public ResponseEntity<List<UserDev>> getAllDevs() {return  ResponseEntity.ok(userDevService.searchAll());}

    @GetMapping("/{id}")
    public ResponseEntity<UserDev> getDev(@PathVariable Long id) {

        UserDev userDev = userDevService.search(id).orElse((null));

        return ResponseEntity.ok(userDev);
    }


    @GetMapping("/{games}")
    public ResponseEntity<List<Game>> getDevGames(@PathVariable Long id){
        UserDev userDev = userDevService.search(id).orElse((null));

        if (userDev != null){
            List<Game> ownedGames = userDev.getOwnedGames();
            return ResponseEntity.ok(ownedGames);
        }
        else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();        }
    }


    @PostMapping()
    public ResponseEntity<UserDev> createDev(@RequestBody UserDev userDev){

        return ResponseEntity.ok(userDevService.create(userDev));
    }

    @PutMapping()
    public ResponseEntity<UserDev> updateDev(@RequestBody UserDev userDev){
        ResponseEntity<UserDev> response = null;

        if (userDev.getId() != null && userDevService.search(userDev.getId()).isPresent())
            response = ResponseEntity.ok(userDevService.update(userDev));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevs(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (userDevService.search(id).isPresent()){
            userDevService.delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
