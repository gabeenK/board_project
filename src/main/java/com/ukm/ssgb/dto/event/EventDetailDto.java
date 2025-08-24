package com.ukm.ssgb.dto.event;

import com.ukm.ssgb.dto.AttachmentDto;
import com.ukm.ssgb.model.Event;
import com.ukm.ssgb.type.EventType;
import com.ukm.ssgb.type.ScreenType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class EventDetailDto {
    private final String title;
    private final ScreenType screenType;
    private final EventType eventType;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final boolean active;
    private final String content;
    private final List<AttachmentDto> attachments;

    public static EventDetailDto toDto(Event event) {
        return EventDetailDto.builder()
                .title(event.getTitle())
                .screenType(event.getScreenType())
                .eventType(event.getEventType())
                .startAt(event.getStartAt())
                .endAt(event.getEndAt())
                .active(event.getActive())
                .content(event.getContent())
                .attachments(event.getEventFiles().stream().map(AttachmentDto::toDto).toList())
                .build();
    }
}
