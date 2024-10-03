package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.GetAllUsersDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetUserByIdDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetUserForUpdateProfileDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.UserForCreationDto;
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
    public ResponseEntity<List<GetAllUsersDto>> GetAllDevs() {
        return  ResponseEntity.ok(userDevService.GetAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdDto> GetDev(@PathVariable Long id) {
        GetUserByIdDto userDev = userDevService.GetById(id);

        if (userDev != null) {
            return ResponseEntity.ok(userDev);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping()
    public ResponseEntity<String> createDev(@RequestBody UserForCreationDto userDev){
        userDevService.Create(userDev);

        return ResponseEntity.ok().body("UserDev created");
    }

    @PutMapping()
    public ResponseEntity<GetUserForUpdateProfileDto> updateDev(@RequestBody GetUserForUpdateProfileDto userDev, @PathVariable Long userDevId){
        ResponseEntity<GetUserForUpdateProfileDto> response = null;

        if (userDevId != null && userDevService.GetById(userDevId) != null) {
            userDevService.UpdateProfile(userDev, userDevId);
            response = ResponseEntity.ok(userDev);
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevs(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (userDevService.GetById(id) != null){
            userDevService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
