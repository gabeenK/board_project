package com.ukm.ssgb.dto.chatroom;

import lombok.Getter;

import java.util.List;

@Getter
public final class ChatRoomsDto {
    private final long totalCount;
    private final List<ChatRoomDto> chatRooms;

    public ChatRoomsDto(List<ChatRoomDto> chatRooms) {
        this.totalCount = chatRooms.size();
        this.chatRooms = chatRooms;
    }
}