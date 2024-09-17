package ShowAndGame.ShowAndGame.Util;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserDev;
import ShowAndGame.ShowAndGame.Services.JwtService;
import ShowAndGame.ShowAndGame.Services.UserDevService;
import ShowAndGame.ShowAndGame.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CurrentUserUtil {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDevService userDevService;

    @Autowired
    public CurrentUserUtil(UserService userService, JwtService jwtService, UserDevService userDevService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDevService = userDevService;
    }

    public String GetJwtToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getCredentials();
    }

    public Long GetCurrentUserId(){
        Optional<User> user = userService.GetByUserName(jwtService.ExtractUsername(GetJwtToken()));
        User currentUser = null;
        Long currentUserId = null;
        if (user.isPresent()){
            currentUser = user.get();
            currentUserId = currentUser.getId();
        }
        return currentUserId;
    }

    public Long GetCurrentUserDevId(){
        Optional<UserDev> userDev = userDevService.GetByUserName(jwtService.ExtractUsername(GetJwtToken()));
        UserDev currentUserDev = null;
        Long currentUserDevId = null;
        if (userDev.isPresent()){
            currentUserDev = userDev.get();
            currentUserDevId = currentUserDev.getId();
        }
        return currentUserDevId;
    }

    public List<Game> GetUserDevGames(){
        Optional<UserDev> userDev = userDevService.GetByUserName(jwtService.ExtractUsername(GetJwtToken()));
        UserDev currentUserDev = null;
        List<Game> ownedGames = null;
        if (userDev.isPresent()){
            currentUserDev = userDev.get();
            ownedGames = currentUserDev.getOwnedGames();
        }
        return ownedGames;
    }
}
