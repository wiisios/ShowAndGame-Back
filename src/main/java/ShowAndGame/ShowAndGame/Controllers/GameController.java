package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok(gameService.searchAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {

        Game game = gameService.search(id).orElse((null));

        return ResponseEntity.ok(game);
    }


    @PostMapping()
    public ResponseEntity<Game> createGame(@RequestBody Game game){

        return ResponseEntity.ok(gameService.create(game));
    }

    @PutMapping()
    public ResponseEntity<Game> updateGame(@RequestBody Game game){
        ResponseEntity<Game> response = null;

        if (game.getId() != null && gameService.search(game.getId()).isPresent())
            response = ResponseEntity.ok(gameService.update(game));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (gameService.search(id).isPresent()){
            gameService.delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Eliminado");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }
}