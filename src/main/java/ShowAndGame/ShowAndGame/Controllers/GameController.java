package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.*;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Services.FollowService;
import ShowAndGame.ShowAndGame.Services.GameService;
import ShowAndGame.ShowAndGame.Util.CurrentUserUtil;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
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

    @GetMapping("/all")
    public ResponseEntity<List<Game>> GetAllGames() {
        return ResponseEntity.ok(gameService.GetAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetGameDto> GetGame(@PathVariable Long id) {
        Long userId = currentUserUtil.GetCurrentUserId();
        GetGameDto game = gameService.GetById(id, userId);

        if (game != null) {
            return ResponseEntity.ok(game);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/explore")
    public ResponseEntity<List<GetGameCardDto>> GetGamesForExplore() {
        List<GetGameCardDto> gameDTOs = gameService.GetAllForExplore();

        return ResponseEntity.ok(gameDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetGameCardDto>> GetGamesByUser(@PathVariable Long userId) {
        List<GetGameCardDto> gamesByUserDto = gameService.GetGameForUserProfile(userId);

        return ResponseEntity.ok(gamesByUserDto);
    }

    @GetMapping("/dev/{devId}")
    public ResponseEntity<List<GetGamesForDeveloperDto>> GetGamesByDeveloper(@PathVariable Long devId) {
        List<GetGamesForDeveloperDto> gamesByDevDto = gameService.GetGamesByDeveloper(devId);

        return ResponseEntity.ok(gamesByDevDto);
    }

    @PostMapping()
    public ResponseEntity<String> CreateGame(@RequestBody GameForCreationAndUpdateDto newGame) {
        Long currentUserId = currentUserUtil.GetCurrentUserId();
        ResponseEntity<String> response = null;

        if (currentUserId != null) {
            gameService.Create(newGame, currentUserId);
            response = ResponseEntity.ok().body("Game created");
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }

    @PutMapping("/update/{gameId}")
    public ResponseEntity<GameForCreationAndUpdateDto> UpdateGame(@RequestBody GameForCreationAndUpdateDto gameToUpdate, @PathVariable Long gameId) {
        ResponseEntity<GameForCreationAndUpdateDto> response = null;
        Long userId = currentUserUtil.GetCurrentUserId();

        if (gameId != null && gameService.GetById(gameId, userId) != null) {
            gameService.Update(gameToUpdate, gameId);
            response = ResponseEntity.ok(gameToUpdate);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteGame(@PathVariable Long id) {
        ResponseEntity<String> response = null;
        Long userDevId = currentUserUtil.GetCurrentUserId();

        if (gameService.GetById(id, userDevId) != null) {
            gameService.Delete(id, userDevId);
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
        }
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @PutMapping("/follow/{gameId}")
    public ResponseEntity<String> Follow(@PathVariable Long gameId) {
        Long userId = currentUserUtil.GetCurrentUserId();
        followService.toggleFollow(userId, gameId);

        boolean isFollowed = followService.isFollowedCheck(userId, gameId);
        String response = isFollowed ? "Follow added" : "Followed removed";

        return ResponseEntity.ok(response);
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportPdf() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("gamesReport", "gameReport.pdf");

        return ResponseEntity.ok().headers(headers).body(gameService.exportPdf());
    }
}