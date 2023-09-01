package com.example.shoppinglist.service;

import com.example.shoppinglist.config.JwtConfig;
import com.example.shoppinglist.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private JwtConfig jwtConfig;

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public boolean isValidToken(UserDetails user, String token) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isExpired(token);
    }

    public String generateToken(User user) {
        return Jwts
            .builder()
            .setSubject(user.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 24 * 60))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64URL.decode(jwtConfig.getJwtSecretKey());
        return Keys.hmacShaKeyFor(bytes);
    }

    private Date extractExpirationDate(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private boolean isExpired(String token) {
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }

}
