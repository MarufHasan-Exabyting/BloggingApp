package com.example.BloggingApplication.service;

import com.example.BloggingApplication.exception.JWTAuthenticationException;
import com.example.BloggingApplication.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.Function;

@Service
public class JWTService {


    private String secretKey = null;

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sKey = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sKey.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        Role role = (userName.equals("admin") ? Role.ROLE_ADMIN : Role.ROLE_USER);
        claims.put("Roles", role);
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30 * 1000))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        String userName = extractClaim(token, Claims::getSubject);
        return userName;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) 
    {
        final Claims claims = extractAllClaims(token);
        if(claims == null)
        {
            return null;
        }
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        try
        {
            Claims claims = Jwts.parser().verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
            return claims;
        }
        catch (NullPointerException nullPointerException)
        {
            return null;
        }
        catch (JwtException jwtException)
        {
            return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        if(userName == null)
        {
            System.out.println("Here userName null");
            return false;
        }
        else
        {
            return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public Role extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return Role.valueOf(claims.get("Roles").toString());
    }
}
