package com.hiring.gatewayserver.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "supersecretkeyformyjwt1234567890!";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String extractUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String extractUserId(String token) {
        return getClaimsFromToken(token).get("userId", String.class);
    }

    public String extractRole(String token) {
        return getClaimsFromToken(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
