package com.ukm.ssgb.dto.chatroom;

import com.ukm.ssgb.decorator.code.ChatSenderCode;
import com.ukm.ssgb.dto.chat.SenderDto;
import com.ukm.ssgb.model.Chat;
import com.ukm.ssgb.model.ChatRoom;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomDto implements ChatSenderCode {

    private final Long chatRoomId;
    private final String chatRoomName;
    private final LocalDateTime createdAt;
    private final String lastChat;
    private final Long userId;

    private SenderDto sender;

    public ChatRoomDto(ChatRoom chatRoom, Chat lastChat) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.chatRoomName = chatRoom.getChatRoomName();
        this.createdAt = chatRoom.getCreatedAt();
        this.lastChat = lastChat.getContentByType();
        this.userId = lastChat.getUserId();
    }

    @Override
    public void setSender(SenderDto sender) {
        this.sender = sender;
    }
}
