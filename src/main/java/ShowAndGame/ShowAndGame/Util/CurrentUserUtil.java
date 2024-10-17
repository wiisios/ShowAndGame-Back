package ShowAndGame.ShowAndGame.Util;

import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Services.JwtService;
import ShowAndGame.ShowAndGame.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUserUtil {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public CurrentUserUtil(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public String GetJwtToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getCredentials();
    }

    public Long GetCurrentUserId(){
        Integer userId = jwtService.ExtractIdClaim(GetJwtToken(), "id");

        return Long.valueOf(userId);
    }
}
