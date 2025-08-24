package com.ukm.ssgb.dto;

import com.ukm.ssgb.model.Nickname;
import lombok.Getter;

@Getter
public class NicknameDto {

    private final Long nicknameId;
    private final String nickname;

    public static NicknameDto of(Nickname nickname) {
        return new NicknameDto(nickname);
    }

    private NicknameDto(Nickname nickname) {
        this.nicknameId = nickname.getNicknameId();
        this.nickname = nickname.getNickName();
    }
}
