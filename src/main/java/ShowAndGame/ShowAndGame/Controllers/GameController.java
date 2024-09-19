package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetGameDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetGameForExploreDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserDev;
import ShowAndGame.ShowAndGame.Services.GameService;
import ShowAndGame.ShowAndGame.Util.CurrentUserUtil;
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

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @GetMapping
    public ResponseEntity<List<GetGameDto>> GetAllGames() {
        return ResponseEntity.ok(gameService.GetAll());
    }


    //hacer 1 endpoint para follow y unfollow (seria un PUT updateando la lista de seguidores)

    @GetMapping("/{id}")
    public ResponseEntity<Game> GetGame(@PathVariable Long id) {

        Game game = gameService.GetById(id).orElse((null));

        if (game != null){
            return ResponseEntity.ok(game);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/feed")
    public ResponseEntity<List<GetGameForExploreDto>> GetGamesForExplore() {
        List<GetGameForExploreDto> gameDTOs = gameService.GetAllForFeed();
        return ResponseEntity.ok(gameDTOs);
    }

    @PostMapping()
    public ResponseEntity<String> CreateGame(@RequestBody GameForCreationAndUpdateDto newGame, List<Tag> tags){
        Long currentUserDevId = currentUserUtil.GetCurrentUserDevId();
        gameService.Create(newGame, currentUserDevId, tags);

        return ResponseEntity.ok().body("Game created");
    }

    @PutMapping()
    public ResponseEntity<GetGameDto> UpdateGame(@RequestBody GetGameDto gameToUpdate){
        ResponseEntity<GetGameDto> response = null;

        if (gameToUpdate.getId() != null && gameService.GetById(gameToUpdate.getId()).isPresent()){
            gameService.Update(gameToUpdate);
            response = ResponseEntity.ok(gameToUpdate);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteGame(@PathVariable Long id){
        ResponseEntity<String> response = null;

        if (gameService.GetById(id).isPresent()){
            gameService.Delete(id);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<String> FollowUnfollow(@RequestBody String state, @PathVariable Long gameId){
        Long userId = currentUserUtil.GetCurrentUserId();
        ResponseEntity<String> response = null;

        if(state == "follow"){
            gameService.Follow(userId, gameId);
            response = ResponseEntity.ok().body("Followed");
        }
        else if(state == "unfollow"){
            gameService.Unfollow(userId, gameId);
            response = ResponseEntity.ok().body("Unfollowed");
        }
        else{
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return response;
    }
}