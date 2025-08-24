package com.ukm.ssgb.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BatchType {
    DELETE_NICKNAME_COUNT("닉네임 변경 기록 삭제", LocalCacheType.NICKNAME_COUNT),
    ;

    private final String subject;
    private final LocalCacheType cacheType;
}
