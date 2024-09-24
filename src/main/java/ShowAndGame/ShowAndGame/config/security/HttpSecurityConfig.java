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
                .csrf( csrfConfig -> csrfConfig.disable() )
                .sessionManagement( sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authConfig -> {

                    // Public Url
                    authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/users").permitAll();
                    authConfig.requestMatchers("/error").permitAll();

                    // Private Url
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
}
