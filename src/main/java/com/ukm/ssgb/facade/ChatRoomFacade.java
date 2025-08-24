package com.ukm.ssgb.facade;

import com.ukm.ssgb.decorator.ChatDecorator;
import com.ukm.ssgb.decorator.filler.ChatSenderFiller;
import com.ukm.ssgb.dto.chat.ChatDto;
import com.ukm.ssgb.dto.chatroom.ChatRoomDetailDto;
import com.ukm.ssgb.dto.chatroom.ChatRoomDto;
import com.ukm.ssgb.dto.chatroom.ChatRoomIdDto;
import com.ukm.ssgb.dto.chatroom.ChatRoomsDto;
import com.ukm.ssgb.model.Chat;
import com.ukm.ssgb.model.ChatRoom;
import com.ukm.ssgb.repository.ChatRepository;
import com.ukm.ssgb.service.ChatRoomService;
import com.ukm.ssgb.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomFacade {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;

    private final ChatSenderFiller chatSenderFiller;
    private final ChatDecorator chatDecorator;

    private final ChatRepository chatRepository;

    public ChatRoomIdDto createChatRoom(long ownUserId, Long otherUserId) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(ownUserId, otherUserId);
        return new ChatRoomIdDto(chatRoom.getChatRoomId());
    }

    @Transactional(readOnly = true)
    public ChatRoomsDto getChatRoom(long ownUserId) {
        List<ChatRoom> chatRooms = chatRoomService.getChatRoomByUserId(ownUserId);

        List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            Chat lastChat = chatService.getLastChat(chatRoom.getChatRoomId());
            ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoom, lastChat);
            chatSenderFiller.fill(chatRoomDto);
            chatRoomDtoList.add(chatRoomDto);
        }

        return new ChatRoomsDto(chatRoomDtoList);
    }

    @Transactional(readOnly = true)
    public ChatRoomDetailDto getChatRoomDetails(Long chatRoomId, PageRequest pageRequest) {
        String chatRoomName = chatRoomService.getChatRoomName(chatRoomId);

        Page<Chat> chatPage = chatRepository.findByChatRoomIdCreatedAtDesc(chatRoomId, pageRequest);
        List<ChatDto> chats = chatPage.get()
                .map(ChatDto::new)
                .toList();
        chats.forEach(chatDecorator::decorate);

        long totalCount = chatPage.getTotalElements();

        return new ChatRoomDetailDto(chatRoomName, chats, totalCount);
    }
}
