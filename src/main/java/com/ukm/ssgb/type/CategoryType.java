package com.ukm.ssgb.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
    REGION("지역"),
    TOPIC("토픽"),
    ;

    private final String categoryTypeName;
}
