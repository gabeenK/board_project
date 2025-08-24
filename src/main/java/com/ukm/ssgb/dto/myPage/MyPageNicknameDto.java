package com.ukm.ssgb.dto.myPage;

import com.ukm.ssgb.decorator.code.NicknameGenerationRemainingCode;
import com.ukm.ssgb.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class MyPageNicknameDto implements NicknameGenerationRemainingCode {
    private Long userId;
    private String nickname;
    @Setter
    private int nicknameGenerationRemaining;

    public static MyPageNicknameDto toDto(User user) {
        return MyPageNicknameDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .build();
    }
}
