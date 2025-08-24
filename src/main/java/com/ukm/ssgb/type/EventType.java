package com.ukm.ssgb.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    ROLLING("롤링배너"),
    POPUP("팝업"),
    GUIDE("가이드");

    private final String displayName;
}
