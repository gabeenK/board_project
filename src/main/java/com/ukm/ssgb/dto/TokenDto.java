package com.ukm.ssgb.dto;

import com.ukm.ssgb.model.User;
import lombok.Getter;

@Getter
public class TokenDto {
    private final Long userId;
    private final String email;
    private final String nickname;
    private final String role;
    private final Long profileImageId;

    public TokenDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole().name();
        this.profileImageId = user.getProfileImageId();
    }
}