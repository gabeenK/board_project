package com.ukm.ssgb.util;

import com.ukm.ssgb.dto.CustomUserDetailsImpl;
import com.ukm.ssgb.dto.TokenDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class TokenUtil {
    public static TokenDto getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(authentication) && authentication.getPrincipal() instanceof CustomUserDetailsImpl userDetails) {
            return userDetails.getTokenDto();
        }
        return null;
    }

    public static TokenDto getUserInfoWithThrow() {
        TokenDto tokenDto = TokenUtil.getUserInfo();
        if (isNull(tokenDto)) {
            throw new IllegalArgumentException("Token 정보가 null 입니다.");
        }

        return tokenDto;
    }

    public static long getIdWithThrow() {
        TokenDto tokenDto = getUserInfoWithThrow();
        if (isNull(tokenDto.getUserId())) {
            throw new IllegalArgumentException("Token 정보의 사용자 Id가 null 입니다.");
        }
        return tokenDto.getUserId();
    }

    public static String getEmailWithThrow() {
        TokenDto tokenDto = getUserInfoWithThrow();
        if (isNull(tokenDto.getEmail())) {
            throw new IllegalArgumentException("Token 정보의 사용자 이메일이 null 입니다.");
        }
        return tokenDto.getEmail();
    }

    public static String getNicknameWithThrow() {
        TokenDto tokenDto = getUserInfoWithThrow();
        if (isNull(tokenDto.getNickname())) {
            throw new IllegalArgumentException("Token 정보의 사용자 닉네임이 null 입니다.");
        }
        return tokenDto.getNickname();
    }

    public static Long getIdWithNull() {
        TokenDto tokenDto = TokenUtil.getUserInfo();
        if (isNull(tokenDto)) {
            return null;
        }
        return tokenDto.getUserId();
    }

    public static Long getProfileIdWithNull() {
        TokenDto tokenDto = getUserInfoWithThrow();
        if (isNull(tokenDto.getProfileImageId())) {
            return null;
        }
        return tokenDto.getProfileImageId();
    }
}