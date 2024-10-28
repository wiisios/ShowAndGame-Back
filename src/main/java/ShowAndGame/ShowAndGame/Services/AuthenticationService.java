package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.AuthenticationDto.AuthenticationRequest;
import ShowAndGame.ShowAndGame.Persistence.Dto.AuthenticationDto.AuthenticationResponse;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse Login(AuthenticationRequest authRequest) {

        //Generating useful token for security
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(),authRequest.getPassword()
        );

        //Letting security check token value
        authenticationManager.authenticate(authToken);

        //Looking up for the user and generating a String with jwt token
        User user = userRepository.findByUserName(authRequest.getUserName()).get();
        String jwt = jwtService.GenerateToken(user, GenerateExtraClaims(user));

        return new AuthenticationResponse(jwt);
    }

    private Map<String, Object> GenerateExtraClaims(User user) {

        //Creating a Map with needed claims
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());
        extraClaims.put("name", user.getUsername());
        extraClaims.put("role", "ROLE_" + user.getUserRole().toString());

        return extraClaims;
    }
}
