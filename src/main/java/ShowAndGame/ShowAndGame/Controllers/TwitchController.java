package ShowAndGame.ShowAndGame.Controllers;

import ShowAndGame.ShowAndGame.Services.TwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TwitchController {
    @Autowired
    private TwitchService twitchService;

    @GetMapping("/games/{gameName}/streams")
    public ResponseEntity<?> getStreams(@PathVariable String gameName) throws UnsupportedEncodingException {
        twitchService.getTwitchToken();
        String gameId = twitchService.getGameIdByName(gameName);
        List<Map<String, Object>> streamList = twitchService.getStreamsByGame(gameId);

        if (streamList == null || streamList.isEmpty() || gameId == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No streams found for this game");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        else {
            return ResponseEntity.ok(streamList);
        }
    }
}
