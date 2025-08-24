package com.ukm.ssgb.dto.user;

import com.ukm.ssgb.decorator.code.FilePathCode;
import com.ukm.ssgb.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class UserInfoDto implements FilePathCode {

    private final String nickname;
    private final Long fileId;
    private final Long profileImageId;
    @Setter
    private  String filePath;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastLoginAt;

    @Builder
    private UserInfoDto(String nickname, Long profileImageId, String email, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.nickname = nickname;
        this.fileId = profileImageId;
        this.profileImageId = profileImageId;
        this.email = email;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public static UserInfoDto toDto(User user) {
        return UserInfoDto.builder()
                .nickname(user.getNickname())
                .profileImageId(user.getProfileImageId())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
