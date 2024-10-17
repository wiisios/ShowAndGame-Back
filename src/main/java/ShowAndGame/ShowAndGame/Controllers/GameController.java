package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.*;
import ShowAndGame.ShowAndGame.Services.FollowService;
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

    @Autowired
    private FollowService followService;

    @GetMapping
    public ResponseEntity<List<GetGameCardDto>> GetAllGames() {
        return ResponseEntity.ok(gameService.GetAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetGameDto> GetGame(@PathVariable Long id) {
        Long userId = currentUserUtil.GetCurrentUserId();
        GetGameDto game = gameService.GetById(id, userId);

        if (game != null){
            return ResponseEntity.ok(game);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/explore")
    public ResponseEntity<List<GetGameCardDto>> GetGamesForExplore() {
        List<GetGameCardDto> gameDTOs = gameService.GetAllForExplore();
        return ResponseEntity.ok(gameDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetGameDto>> GetGamesByUser(@PathVariable Long userId) {
        List<GetGameDto> gamesByUserDtos = gameService.GetGameForUserProfile(userId);
        return ResponseEntity.ok(gamesByUserDtos);
    }

    @GetMapping("/dev/{devId}")
    public ResponseEntity<List<GetGamesForDeveloperDto>> GetGamesByDeveloper(@PathVariable Long devId){
        List<GetGamesForDeveloperDto> gamesByDevDtos = gameService.getGamesByDeveloper(devId);
        return ResponseEntity.ok(gamesByDevDtos);
    }

    @PostMapping()
    public ResponseEntity<String> CreateGame(@RequestBody GameForCreationDto newGame){
        Long currentUserId = currentUserUtil.GetCurrentUserId();
        ResponseEntity<String> response = null;

        if (currentUserId != null){
        gameService.Create(newGame, currentUserId);
        response = ResponseEntity.ok().body("Game created");
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }

    @PutMapping()
    public ResponseEntity<GetGamesForDeveloperDto> UpdateGame(@RequestBody GetGamesForDeveloperDto gameToUpdate){
        ResponseEntity<GetGamesForDeveloperDto> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();

        if (gameToUpdate.getId() != null && gameService.GetById(gameToUpdate.getId(), userId) != null){
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
        Long userDevId = currentUserUtil.GetCurrentUserId();

        if (gameService.GetById(id, userDevId) != null){
            gameService.Delete(id, userDevId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<String> Follow(@PathVariable Long gameId){
        Long userId = currentUserUtil.GetCurrentUserId();

        followService.toggleFollow(userId, gameId);

        boolean isFollowed = followService.isFollowedCheck(userId, gameId);

        String response = isFollowed ? "Follow added" : "Followed removed";

        return ResponseEntity.ok(response);
    }
}