package com.ukm.ssgb.dto.combo;

import com.ukm.ssgb.type.EventType;

public record EventTypeDto(String eventType, String eventTypeName) {

    public static EventTypeDto toDto(EventType eventType) {
        return new EventTypeDto(eventType.name(), eventType.getDisplayName());
    }
}
