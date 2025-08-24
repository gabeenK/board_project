package com.ukm.ssgb.dto.chat;

import com.ukm.ssgb.model.User;
import lombok.Getter;

@Getter
public class SenderDto {
    private final Long userId;
    private final String nickname;
    private final Long profileImageId;

    public SenderDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profileImageId = user.getProfileImageId();
    }
}
