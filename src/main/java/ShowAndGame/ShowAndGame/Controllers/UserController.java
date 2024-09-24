package ShowAndGame.ShowAndGame.Controllers;


import ShowAndGame.ShowAndGame.Persistence.Dto.GetAllUsersDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetUserByIdDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetUserForUpdateProfileDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.UserForCreationDto;
import ShowAndGame.ShowAndGame.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<GetAllUsersDto>> getAllUsers() {return  ResponseEntity.ok(userService.GetAll());}

    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdDto> getUser(@PathVariable Long id) {

        GetUserByIdDto user = userService.GetById(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping()
    public ResponseEntity<String> CreateUser(@RequestBody UserForCreationDto user){
        userService.Create(user);

        return ResponseEntity.ok().body("User created");
    }

    @PutMapping()
    public ResponseEntity<GetUserForUpdateProfileDto> UpdateUserProfile(@RequestBody GetUserForUpdateProfileDto userToUpdate){
        ResponseEntity<GetUserForUpdateProfileDto> response = null;

        if (userToUpdate.getId() != null && userService.GetById(userToUpdate.getId()) != null){
            userService.UpdateProfile(userToUpdate);
            response = ResponseEntity.ok(userToUpdate);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (userService.GetById(id) != null){
            userService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
