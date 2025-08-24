package com.ukm.ssgb.dto.chatroom;

import com.ukm.ssgb.dto.chat.ChatDto;

import java.util.List;

public record ChatRoomDetailDto(String chatRoomName, List<ChatDto> chats, long totalCount) {
}
