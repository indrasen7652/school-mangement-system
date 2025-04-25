package com.school.management.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtService {

    private static final String SECRET = "406886006579F58D9175C6451DC26C1831797ED6A55CFA64619213C6E81D0D150C9AC7AE2BAE177E018AA1EE12C93BEAA0E1336DDA84C723D4146EC08E3389C5";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(1440);

    public Map<String, String> generateToken(UserDetails userDetails) {
        Map<String, String> result = new HashMap<>();
        Map<String, Object> claims = new HashMap<>();
        Date issuedAt = Date.from(Instant.now());
        claims.put("role", userDetails.getAuthorities().toString());
        Date expiration = Date.from(Instant.now().plusMillis(VALIDITY));
        result.put("username", userDetails.getUsername());
        result.put("issuedAt", String.valueOf(issuedAt));
        result.put("expiration", String.valueOf(expiration));
        result.put("message", "Login successfully.");

        // Generate the token
        result.put("token", Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact());
        return result;
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    public boolean isTokenValidForRT(String jwt) {
        try {
            getClaims(jwt);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractRole(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parser()
                    .setSigningKey(SECRET)
                    .build();

            Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
            return claims.get("role", String.class);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to extract role from JWT", e);
        }
    }

}

