package ShowAndGame.ShowAndGame.Controllers;


import ShowAndGame.ShowAndGame.Persistence.Entities.User;
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

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {return  ResponseEntity.ok(userService.GetAll());}

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {

        User user = userService.GetById(id).orElse((null));

        return ResponseEntity.ok(user);
    }


    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){

        return ResponseEntity.ok(userService.Create(user));
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user){
        ResponseEntity<User> response = null;

        if (user.getId() != null && userService.GetById(user.getId()).isPresent())
            response = ResponseEntity.ok(userService.Update(user));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (userService.GetById(id).isPresent()){
            userService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}
