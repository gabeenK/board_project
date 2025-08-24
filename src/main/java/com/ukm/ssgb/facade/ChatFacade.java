package com.ukm.ssgb.facade;

import com.ukm.ssgb.dto.chat.ChatCreateRequestDto;
import com.ukm.ssgb.dto.chat.ChatIdDto;
import com.ukm.ssgb.model.Chat;
import com.ukm.ssgb.service.ChatPublishService;
import com.ukm.ssgb.service.ChatService;
import com.ukm.ssgb.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatFacade {

    private final ChatService chatService;
    private final ChatPublishService chatPublishService;

    public ChatIdDto createChat(ChatCreateRequestDto requestDto) {
        long userId = TokenUtil.getIdWithThrow();
        Long chatRoomId = requestDto.getChatRoomId();
        String content = requestDto.getContent();
        List<MultipartFile> attachments = requestDto.getAttachments();

        Chat chat = chatService.createChat(chatRoomId, userId, content, attachments);
        chatPublishService.publish(chat);

        return new ChatIdDto(chat.getChatId());
    }
}
