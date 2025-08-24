package com.ukm.ssgb.dto.event;

import com.ukm.ssgb.dto.AttachmentDto;
import com.ukm.ssgb.model.Event;
import com.ukm.ssgb.type.EventType;
import lombok.Getter;

import java.util.List;

@Getter
public class EventListByUserDto {
    private final long eventId;
    private final EventType eventType;
    private final String link;
    private final String title;
    private final String content;
    private final List<AttachmentDto> attachments;

    public EventListByUserDto(Event event) {
        this.eventId = event.getEventId();
        this.eventType = event.getEventType();
        this.link = event.getLink();
        this.title = event.getTitle();
        this.content = event.getContent();
        this.attachments = event.getEventFiles().stream().map(AttachmentDto::toDto).toList();
    }
}
