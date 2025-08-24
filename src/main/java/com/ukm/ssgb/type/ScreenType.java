package com.ukm.ssgb.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScreenType {
    BOARD("게시판"),
    CHAT("채팅"),
    MY_PAGE("마이페이지"),
    REGISTRATION("회원가입");

    private final String displayName;
}
