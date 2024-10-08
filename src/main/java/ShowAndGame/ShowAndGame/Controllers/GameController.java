package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GameForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGameDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGameForExploreDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGamesByUserDto;
import ShowAndGame.ShowAndGame.Services.GameService;
import ShowAndGame.ShowAndGame.Util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/{id}")
    public ResponseEntity<GetGameDto> GetGame(@PathVariable Long id) {

        GetGameDto game = gameService.GetById(id);

        if (game != null){
            return ResponseEntity.ok(game);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/explore")
    public ResponseEntity<List<GetGameForExploreDto>> GetGamesForExplore() {
        List<GetGameForExploreDto> gameDTOs = gameService.GetAllForExplore();
        return ResponseEntity.ok(gameDTOs);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetGamesByUserDto>> GetGamesByUser(Long userId) {
        List<GetGamesByUserDto> gamesByUserDtos = gameService.GetGameByUser(userId);
        return ResponseEntity.ok(gamesByUserDtos);
    }

    @GetMapping("/developer/{userId}")
    public ResponseEntity<List<GetGamesByUserDto>> GetGamesByDeveloper(Long userId) {
        List<GetGamesByUserDto> gamesByDeveloperDtos = gameService.GetGameByDeveloper(userId);
        return ResponseEntity.ok(gamesByDeveloperDtos);
    }



    @PostMapping()
    public ResponseEntity<String> CreateGame(@RequestBody GameForCreationDto newGame){
        Long currentUserDevId = currentUserUtil.GetCurrentUserDevId();
        gameService.Create(newGame, currentUserDevId);

        return ResponseEntity.ok().body("Game created");
    }

    @PutMapping()
    public ResponseEntity<GetGameDto> UpdateGame(@RequestBody GetGameDto gameToUpdate){
        ResponseEntity<GetGameDto> response = null;

        if (gameToUpdate.getId() != null && gameService.GetById(gameToUpdate.getId()) != null){
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
        Long userDevId = currentUserUtil.GetCurrentUserDevId();

        if (gameService.GetById(id) != null){
            gameService.Delete(id, userDevId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");}
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<String> FollowUnfollow(@RequestBody String state, @PathVariable Long gameId){
        Long userId = currentUserUtil.GetCurrentUserId();
        ResponseEntity<String> response = null;

        if(Objects.equals(state, "follow")){
            gameService.Follow(userId, gameId);
            response = ResponseEntity.ok().body("Followed");
        }
        else if(Objects.equals(state, "unfollow")){
            gameService.Unfollow(userId, gameId);
            response = ResponseEntity.ok().body("Unfollowed");
        }
        else{
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return response;
    }
}