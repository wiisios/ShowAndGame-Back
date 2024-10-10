package ShowAndGame.ShowAndGame.config.security.Filter;

import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import ShowAndGame.ShowAndGame.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1. Obtaining the Header that has JWT
        String authHeader = request.getHeader("Authorization"); // Bearer jwt

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //2. Obtaining JWT from that Header
        String jwt = authHeader.split(" ")[1];

        //3. Obtaining subject from jwt
        String username = jwtService.ExtractUsername(jwt);

        //4. Setting an object Authentication inside SecurityContext

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findByUserName(username).orElse(null);
            if (user != null && jwtService.isTokenValid(jwt, user)) {

                // 5. Obtaining roles from JWT (extra claims)
                String role = jwtService.ExtractClaim(jwt, "role");

                // 6. Changing role into GrantedAuthority
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                // 7. Creating an authentication token with extracted role
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, jwt, authorities
                );
                System.out.println(authToken);

                // 8. Setting security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            //9. Running remaining filters

            filterChain.doFilter(request, response);

        }
    }
}
