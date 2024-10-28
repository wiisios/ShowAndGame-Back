package ShowAndGame.ShowAndGame.config.security;

import ShowAndGame.ShowAndGame.config.security.Filter.JwtAuthenticationFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource())) // CORS Config
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> {

                    // Public URL (Swagger)
                    authConfig.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/swagger-ui.html").permitAll();

                    // Public URLs
                    authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/users").permitAll();
                    authConfig.requestMatchers("/error").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/games/{gameName}/streams").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/games/export-pdf").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/games/all").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/feedPosts/export-pdf").permitAll();

                    // Private URLs
                        //Comment
                    authConfig.requestMatchers(HttpMethod.GET, "/comments/post/{postId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.POST, "/comments").hasAnyRole("ADMIN", "USER", "DEVELOPER");

                        //FeedPost
                    authConfig.requestMatchers(HttpMethod.GET, "/feedPosts").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.GET, "/feedPosts/{postId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.GET, "/feedPosts/game/{gameId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.POST, "/feedPosts/{gameId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.PUT, "/feedPosts/like/{postId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");

                        //Game
                    authConfig.requestMatchers(HttpMethod.GET, "/games/{id}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.GET, "/games/explore").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.GET, "/games/user/{userId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.GET, "/games/dev/{devId}").hasAnyRole("ADMIN", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.POST, "/games").hasAnyRole("ADMIN", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.PUT, "/games/update/{gameId}").hasAnyRole("ADMIN", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.PUT, "/games/follow/{gameId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/games/{id}").hasAnyRole("ADMIN", "DEVELOPER");

                        //ReviewPost
                    authConfig.requestMatchers(HttpMethod.GET, "/reviewPosts/gamePosts/{gameId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.POST, "/reviewPosts/{gameId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.PUT, "/reviewPosts/{id}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/reviewPosts/{id}").hasAnyRole("ADMIN", "USER", "DEVELOPER");

                        //Tag
                    authConfig.requestMatchers(HttpMethod.GET, "/tags").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.GET, "/tags/{gameId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.POST, "/tags").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/tags/{tagId}").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/tags/{id}").hasRole("ADMIN");

                        //User
                    authConfig.requestMatchers(HttpMethod.GET, "/users/all").hasAnyRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.PUT, "/users/{userId}").hasAnyRole("ADMIN", "USER", "DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.PUT, "/users/adminUpdate/{userId}").hasAnyRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN");

                        //Like
                    authConfig.requestMatchers(HttpMethod.GET, "/userlikes/all").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.GET, "/userlikes/{id}").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.POST, "/userlikes").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/userlikes/update/{id}").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/userlikes/{id}").hasRole("ADMIN");

                        //Follow
                    authConfig.requestMatchers(HttpMethod.GET, "/follows/all").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.GET, "/follows/{id}").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.POST, "/follows").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT, "/follows/update/{id}").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE, "/UserLikes/{id}").hasRole("ADMIN");

                    // Catch-all for other requests: anyRequest
                    authConfig.anyRequest().denyAll();
                });
        return http.build();
    }


    // CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Frontend permissions
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // CORS to all URL
        return source;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}

