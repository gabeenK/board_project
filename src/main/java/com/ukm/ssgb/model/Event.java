package com.ukm.ssgb.model;

import com.ukm.ssgb.constant.ServiceConstants;
import com.ukm.ssgb.type.EventType;
import com.ukm.ssgb.type.ScreenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long eventId;

    @Setter
    @Column
    private String title;

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private ScreenType screenType;

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Setter
    @Column
    private LocalDateTime startAt;

    @Setter
    @Column
    private LocalDateTime endAt;

    @Setter
    @Column
    private String link;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventFile> eventFiles = new ArrayList<>();

    @Setter
    @Column
    private Boolean active;

    @Setter
    @Column
    private String content;

    @Column
    private Boolean deleted;


    @Builder
    public Event(String title, ScreenType screenType, EventType eventType, LocalDateTime startAt, LocalDateTime endAt, String link, List<FileEntity> fileEntities, Boolean active, String content) {
        this.title = title;
        this.screenType = screenType;
        this.eventType = eventType;
        this.startAt = startAt;
        this.endAt = endAt;
        this.link = link;
        this.eventFiles = fileEntities.stream().map(fileEntity -> EventFile.toEntity(this, fileEntity)).toList();
        this.active = active;
        this.content = content;
        this.deleted = false;
    }

    public boolean isAlways() {
        return ServiceConstants.ALWAYS_START_DATE.equals(startAt) && ServiceConstants.ALWAYS_END_DATE.equals(endAt);
    }

    public void deleteFiles(List<Long> deleteFileIds) {
        Set<Long> deleteFileIdSet = new HashSet<>(deleteFileIds);
        this.eventFiles.removeIf(eventFile -> deleteFileIdSet.contains(eventFile.getFileId()));
    }

    public void updateFiles(List<FileEntity> fileEntities) {
        if (fileEntities.isEmpty()) {
            return;
        }

        this.eventFiles.clear();
        fileEntities.stream()
                .map(file -> new EventFile(this, file.getFileId()))
                .forEach(eventFile -> eventFiles.add(eventFile));
    }

    public void delete() {
        this.deleted = true;
    }
}