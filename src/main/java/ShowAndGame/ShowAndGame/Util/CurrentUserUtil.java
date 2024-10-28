package ShowAndGame.ShowAndGame.Util;

import ShowAndGame.ShowAndGame.Services.JwtService;
import ShowAndGame.ShowAndGame.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class CurrentUserUtil {
    private final JwtService jwtService;

    @Autowired
    public CurrentUserUtil(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String GetJwtToken() {
        //Getting token from context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (String) authentication.getCredentials();
    }

    public Long GetCurrentUserId() {
        //Accessing current token to get id from claim
        Integer userId = jwtService.ExtractIdClaim(GetJwtToken(), "id");

        return Long.valueOf(userId);
    }
}
