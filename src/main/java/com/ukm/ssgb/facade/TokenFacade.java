package com.ukm.ssgb.facade;

import com.ukm.ssgb.dto.LoginDto;
import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.service.JwtManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenFacade {

    private final JwtManager jwtManager;

    public LoginDto refresh(String refreshToken) {
        if (!jwtManager.validateToken(refreshToken)) {
            throw new ValidationException("Bad or expired token.");
        }

        Claims claims = jwtManager.parseClaims(refreshToken);
        long userId = Long.parseLong(claims.getSubject());
        String access = jwtManager.createAccessToken(userId);
        String refresh = jwtManager.createRefreshToken(userId);
        return new LoginDto(access, refresh);
    }
}
