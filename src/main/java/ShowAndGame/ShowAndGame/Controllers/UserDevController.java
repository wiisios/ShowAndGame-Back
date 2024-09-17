package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
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
    public ResponseEntity<List<UserDev>> GetAllDevs() {return  ResponseEntity.ok(userDevService.GetAll());}

    @GetMapping("/{id}")
    public ResponseEntity<UserDev> GetDev(@PathVariable Long id) {
        UserDev userDev = userDevService.GetById(id).orElse((null));

        return ResponseEntity.ok(userDev);
    }

    @PostMapping()
    public ResponseEntity<UserDev> createDev(@RequestBody UserDev userDev){

        return ResponseEntity.ok(userDevService.Create(userDev));
    }

    @PutMapping()
    public ResponseEntity<UserDev> updateDev(@RequestBody UserDev userDev){
        ResponseEntity<UserDev> response = null;

        if (userDev.getId() != null && userDevService.GetById(userDev.getId()).isPresent())
            response = ResponseEntity.ok(userDevService.Update(userDev));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevs(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (userDevService.GetById(id).isPresent()){
            userDevService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
