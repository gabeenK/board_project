package com.ukm.ssgb.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {
    EMAIL("이메일"),
    KAKAO("카카오톡"),
    ;

    private final String displayName;
}
