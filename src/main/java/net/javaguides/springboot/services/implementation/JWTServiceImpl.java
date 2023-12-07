package net.javaguides.springboot.services.implementation;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.javaguides.springboot.services.JWTService;

@Service
public class JWTServiceImpl implements JWTService{
    
    //store and get the secret key in application.properties
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    //same with expiration
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    // generate jwtToken depending on Username 
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // expire after a day in milisec
        .signWith(getSigninKey())
        .compact();
    }

    //to extract the information that you want from the json depending on T
    private <T> T extractClaim(String token, Function<Claims, T> ClaimResolvers){
        final Claims claims = extractAllClaims(token);
        return ClaimResolvers.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // gets the secret signing key, UNSAFE shouldn't be implemented like this
    private SecretKey getSigninKey(){
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    //verify signature of token then extracts all claims from it
    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
    }

    //check if the token is valid by verifying sig and seing if username in it matches userDetails
    //and token not expired
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //get expiration off of token and see that it hasn't passed
    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

}
