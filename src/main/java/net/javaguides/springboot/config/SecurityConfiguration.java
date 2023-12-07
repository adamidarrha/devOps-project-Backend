package net.javaguides.springboot.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.model.Role;
import net.javaguides.springboot.services.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
        //authorize http requests
        //permit everybody to access api/v1/auth/** so they can authenticate
        .authorizeHttpRequests()//request -> request.requestMatchers("api/v1/auth/**").permitAll()
        .requestMatchers("api/v1/auth/**").permitAll()
        //permit only admin to access admin page
        .requestMatchers("api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
        //permit only user role to access user page
        .requestMatchers("api/v1/user").hasAnyAuthority(Role.USER.name())
        //authentication is required for all other endpoints
        .anyRequest().permitAll()

        .and()
        //sets the session creation policy to stateless, indicating that the application
        //should rely on tokens for authentication and not http sessions
        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //sets the jwtAuthenticationFilter that will be reponsible for validating jwt
        .authenticationProvider(authenticationProvider()).addFilterBefore(
            jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
        );

        return http.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //returns userDetails that has the function loadUserByUsername that we implemented
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        //sets the password encoding bean
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();//sets the passwordEncoder to Bcrypt
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
    throws Exception{
        return config.getAuthenticationManager();
    }

}
