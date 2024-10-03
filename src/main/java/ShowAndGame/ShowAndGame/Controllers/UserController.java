package ShowAndGame.ShowAndGame.Controllers;


import ShowAndGame.ShowAndGame.Persistence.Dto.*;
import ShowAndGame.ShowAndGame.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<GetAllUsersDto>> GetAllUsers() {return  ResponseEntity.ok(userService.GetAll());}

    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdDto> GetUser(@PathVariable Long id) {

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

    @PutMapping("/{userId}")
    public ResponseEntity<GetUserForUpdateProfileDto> UpdateUserProfile(@RequestBody GetUserForUpdateProfileDto userToUpdate, @PathVariable Long userId){
        ResponseEntity<GetUserForUpdateProfileDto> response = null;

        if (userId != null && userService.GetById(userId) != null){
            userService.UpdateProfile(userToUpdate, userId);
            response = ResponseEntity.ok(userToUpdate);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteUser(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (userService.GetById(id) != null){
            userService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @PutMapping("/adminUpdate/{userId}")
    public ResponseEntity<String> UpdateUser(@PathVariable Long id, @RequestBody GetUserForAdminUpdateDto user){
        ResponseEntity<String> response = null;

        if (userService.GetById(id) != null){
            userService.UpdateUser(user,id);
            response = ResponseEntity.ok("User updated");
        }
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
