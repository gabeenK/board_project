package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "chat_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long chatFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatId")
    private Chat chat;

    @Column
    private Long fileId;

    @Column
    private Boolean deleted;

    @Builder
    public ChatFile(Chat chat, Long fileId) {
        this.chat = chat;
        this.fileId = fileId;
        this.deleted = false;
    }

    public static ChatFile toEntity(Chat chat, FileEntity fileEntity) {
        return ChatFile.builder()
                .chat(chat)
                .fileId(fileEntity.getFileId())
                .build();
    }
}