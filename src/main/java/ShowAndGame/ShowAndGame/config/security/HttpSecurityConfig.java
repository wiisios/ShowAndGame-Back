package ShowAndGame.ShowAndGame.config.security;

import ShowAndGame.ShowAndGame.config.security.Filter.JwtAuthenticationFilter;
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
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource())) // Configuración CORS
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> {

                    // Public URL (Swagger)
                    authConfig.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/swagger-ui.html").permitAll();

                    // Public URL
                    authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/authentication-controller/login").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/users").permitAll();

                    authConfig.requestMatchers("/error").permitAll();


                    //Private URL
                    authConfig.requestMatchers(HttpMethod.POST,"/comments/**").hasAnyRole()
                            .anyRequest().authenticated();

                    authConfig.requestMatchers(HttpMethod.GET, "/feedPosts/**").hasAnyRole()
                            .anyRequest().authenticated();
                    authConfig.requestMatchers(HttpMethod.POST, "/feedPosts/**").hasAnyRole()
                            .anyRequest().authenticated();

                    authConfig.requestMatchers(HttpMethod.GET,"/reviewPosts/{gameId}").hasAnyRole()
                            .anyRequest().authenticated();
                    authConfig.requestMatchers(HttpMethod.POST,"/reviewPosts/**").hasAnyRole()
                            .anyRequest().authenticated();

                    authConfig.requestMatchers(HttpMethod.GET,"/tags").hasAnyRole()
                            .anyRequest().authenticated();
                    authConfig.requestMatchers(HttpMethod.GET,"/tags/{gameId}").hasAnyRole()
                            .anyRequest().authenticated();
                    authConfig.requestMatchers(HttpMethod.POST,"/tags/{gameId}").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT,"/tags/{gameId}").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.DELETE,"/tags/{gameId}").hasRole("ADMIN");

                    authConfig.requestMatchers(HttpMethod.GET,"/games/{id}").hasAnyRole()
                            .anyRequest().authenticated();
                    authConfig.requestMatchers(HttpMethod.GET,"/games/feed").hasAnyRole()
                            .anyRequest().authenticated();
                    authConfig.requestMatchers(HttpMethod.POST,"/games/**").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT,"/games").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.PUT,"/games/{id}").hasAnyRole()
                            .anyRequest().authenticated();
                    authConfig.requestMatchers(HttpMethod.DELETE,"/games/**").hasRole("ADMIN");

                    authConfig.requestMatchers(HttpMethod.GET,"/users/all").hasRole("ADMIN");
                    authConfig.requestMatchers(HttpMethod.GET,"/users/{id}").hasRole("USER");
                    authConfig.requestMatchers(HttpMethod.POST,"/users/**").hasAnyRole()
                            .anyRequest().authenticated();
                    authConfig.requestMatchers(HttpMethod.PUT,"/users/**").hasAnyRole()
                            .anyRequest().authenticated();

                    authConfig.requestMatchers(HttpMethod.GET,"/users/{id}").hasRole("DEVELOPER");
                    authConfig.requestMatchers(HttpMethod.PUT,"/users/**").hasRole("DEVELOPER");

                    // In case we forgot some Url
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
}
