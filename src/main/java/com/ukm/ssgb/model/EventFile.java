package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "event_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long eventFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId")
    private Event event;

    @Column
    private Long fileId;

    @Builder
    public EventFile(Event event, Long fileId) {
        this.event = event;
        this.fileId = fileId;
    }

    public static EventFile toEntity(Event event, FileEntity fileEntity) {
        return EventFile.builder()
                .event(event)
                .fileId(fileEntity.getFileId())
                .build();
    }
}