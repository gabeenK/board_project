package com.ukm.ssgb.dto.combo;

import com.ukm.ssgb.type.ScreenType;

public record ScreenTypeDto(String screenType, String screenTypeName) {

    public static ScreenTypeDto toDto(ScreenType screenType) {
        return new ScreenTypeDto(screenType.name(), screenType.getDisplayName());
    }
}
