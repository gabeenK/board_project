package com.ukm.ssgb.dto.user;

import com.ukm.ssgb.decorator.code.ProfileImageFilePathCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class AuthorDto implements ProfileImageFilePathCode {
    private String nickname;
    private Long profileImageId;
    @Setter
    private String filePath;
    private Long userId;

    public static AuthorDto of(String nickname, Long profileImageId, Long userId) {
        return AuthorDto.builder()
                .nickname(nickname)
                .profileImageId(profileImageId)
                .userId(userId)
                .build();
    }
}
