package net.javaguides.springboot.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.services.JWTService;
import net.javaguides.springboot.services.UserService;
import org.springframework.web.servlet.HandlerExceptionResolver;

//the goal of this class is to get the request and check for jwt
//if it has jwt it will verify it and set security context for the request with the authorities of the user
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter{
    
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JWTService jwtService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");//get authorization header from request
        final String jwt;
        final String username;
        
        //check if the authHeader is empty or doesn't have Bearer on jwt
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            
            filterChain.doFilter(request, response);
            return;
        }

        try{

        //extract jwt from authorization header
        jwt = authHeader.substring(7);//after the Bearer which is 7 char

        //extract username from jwt
        username = jwtService.extractUsername(jwt);

        //check if username is not empty and we don't have security context
        if(StringUtils.isNotEmpty(username) && 
        SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            if(jwtService.isTokenValid(jwt, userDetails)){//checks if the token is valid

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                //stores authentication information about user in token
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                (userDetails, null ,userDetails.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //generates and sets the security context for the user so the request 
                //has user's identities and authorities
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);


            }

        }
        filterChain.doFilter(request, response);

    } catch(Exception exception){
        handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    
    }

}
