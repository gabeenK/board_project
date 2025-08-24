package com.ukm.ssgb.dto.event;

import com.ukm.ssgb.model.Event;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class EventListDto {
    private final long eventId;
    private final String title;
    private final String eventTypeName;
    private final boolean always;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;
    private final Long duration;
    private final String screenTypeName;
    private final boolean active;

    public EventListDto(Event event) {
        this.eventId = event.getEventId();
        this.title = event.getTitle();
        this.eventTypeName = event.getEventType().getDisplayName();
        this.startAt = event.getStartAt();
        this.endAt = event.getEndAt();
        this.always = event.isAlways();
        this.duration = calculateDurationToHours();
        this.screenTypeName = event.getScreenType().getDisplayName();
        this.active = event.getActive();
    }

    private Long calculateDurationToHours() {
        if (this.always) {
            return null;
        }
        Duration duration = Duration.between(startAt, endAt);
        return duration.toHours();
    }
}
