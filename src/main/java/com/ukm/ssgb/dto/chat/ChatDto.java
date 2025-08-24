package com.ukm.ssgb.dto.chat;

import com.ukm.ssgb.decorator.code.ChatSenderCode;
import com.ukm.ssgb.dto.AttachmentDto;
import com.ukm.ssgb.model.Chat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ChatDto implements ChatSenderCode {

    private final Long chatId;
    private final String content;

    private final Long userId;
    private SenderDto sender;

    private final LocalDateTime createdAt;
    private final List<AttachmentDto> attachments;

    public ChatDto(Chat chat) {
        this.chatId = chat.getChatId();
        this.content = chat.getContent();
        this.createdAt = chat.getCreatedAt();
        this.userId = chat.getUserId();
        this.attachments = chat.getChatFiles().stream().map(AttachmentDto::toDto).toList();
    }

    @Override
    public void setSender(SenderDto sender) {
        this.sender = sender;
    }
}
