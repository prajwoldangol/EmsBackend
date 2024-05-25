package com.prajwol.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    private SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long expiration ;
    public JwtTokenProvider(@Value("${jwt.secret.key}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }


    public String generateToken(String username, HashMap<String, String> claims) {
        Instant now = Instant.now();
        Instant expirationInstant = now.plus(expiration, ChronoUnit.MILLIS);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationInstant))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String generateRefreshToken(String username, HashMap<String, String> claims) {
        Instant now = Instant.now();
        Instant refreshTokenExpiry = now.plus(expiration+5220000, ChronoUnit.MILLIS);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(refreshTokenExpiry))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(Date.from(Instant.now()));
    }

//    public Claims getClaimsFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//    }

//    public boolean isTokenValid(String token) {
//
//        try {
//            Claims claims = getClaimsFromToken(token);
//            Instant expirationInstant = claims.getExpiration().toInstant();
//            return !expirationInstant.isBefore(Instant.now());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String getUsernameFromToken(String token) {
//        return getClaimsFromToken(token).getSubject();
//    }
}
