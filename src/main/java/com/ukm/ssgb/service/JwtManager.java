package com.ukm.ssgb.service;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Service
public class JwtManager {

    private static final long ACCESS_TOKEN_EXPIRED_SECONDS = 60 * 60 * 24; // 1 day
    private static final long REFRESH_TOKEN_EXPIRED_SECONDS = 60 * 60 * 24 * 7; // 1 week

    private final String secret = "126789G-KaPdSgVkYp3s1234/B?E(H+MDdiVwKsdfizlkw@sdifl";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public String createAccessToken(Long userId) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .expiration(Date.from(Instant.now().plusSeconds(ACCESS_TOKEN_EXPIRED_SECONDS)))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .expiration(Date.from(Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRED_SECONDS)))
                .signWith(secretKey)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | SecurityException | MalformedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    public Claims parseClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }
}