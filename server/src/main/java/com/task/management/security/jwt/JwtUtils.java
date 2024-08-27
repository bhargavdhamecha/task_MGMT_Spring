package com.task.management.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {

    @Value("${com.taskManagement.jwtSecret}")
    private String jwtSecret;

    /**
     * to extract username from the token
     * @param token string
     * @return string
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * to generate jwt token
     * @param userDetails UserDetails
     * @param jwtExpiration long
     * @return string
     */
    public String generateToken(UserDetails userDetails, long jwtExpiration) {
        return generateToken(new HashMap<>(), userDetails, jwtExpiration);
    }

    /**
     *
     * @param extraClaims map
     * @param userDetails UserDetails
     * @param jwtExpiration long
     * @return string
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long jwtExpiration) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     *
     * @param token string
     * @param claimsResolver function
     * @return
     * @param <T> generic
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     *
     * @param extraClaims map
     * @param userDetails UserDetails
     * @param expiration long
     * @return string
     */
    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ){
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * to valid the provided jwt token
     * @param token string
     * @param userDetails userDetails
     * @return boolean
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * to check whether the token is expired or not
     * @param token string
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * to extract expiration of provided token
     * @param token string
     * @return Date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     *
     * @param token string
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * to get sign in key
     * @return SecretKey
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
