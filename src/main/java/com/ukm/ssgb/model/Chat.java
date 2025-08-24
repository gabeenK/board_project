package com.ukm.ssgb.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Getter
@Entity
@Table(name = "chat")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long chatId;

    @Column
    private Long chatRoomId;

    @Column
    private Long userId;

    @Column
    private String content;

    @Column
    private Boolean hasAttachments;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatFile> chatFiles = new ArrayList<>();

    public static Chat contentChat(Long chatRoomId, Long userId, String content) {
        return new Chat(chatRoomId, userId, content, emptyList());
    }

    public static Chat attachmentsChat(Long chatRoomId, Long userId, List<FileEntity> fileEntities) {
        return new Chat(chatRoomId, userId, StringUtils.EMPTY, fileEntities);
    }

    private Chat(Long chatRoomId, Long userId, String content, List<FileEntity> fileEntities) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.content = content;
        this.hasAttachments = !fileEntities.isEmpty();
        this.chatFiles = fileEntities.stream().map(fileEntity -> ChatFile.toEntity(this, fileEntity)).toList();
    }

    public String getContentByType() {
        if (hasAttachments) {
            // TODO 파일 형식 별 메시지 구성
            return "사진을 보냈습니다.";
        }

        return content;
    }
}