package com.ukm.ssgb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukm.ssgb.decorator.ChatDecorator;
import com.ukm.ssgb.dto.chat.ChatDto;
import com.ukm.ssgb.model.Chat;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatPublishService {

    private final ChatDecorator chatDecorator;
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRoomService chatRoomService;

    @SneakyThrows
    public void publish(Chat chatToSend) {
        ChatDto chatDto = new ChatDto(chatToSend);
        chatDecorator.decorate(chatDto);

        Long chatRoomId = chatToSend.getChatRoomId();
        String receiverEmail = chatRoomService.getReceiverEmail(chatRoomId, chatToSend.getUserId());
        simpMessagingTemplate.convertAndSendToUser(receiverEmail, getDestination(chatRoomId), objectMapper.writeValueAsString(chatDto));
    }

    private String getDestination(Long chatRoomId) {
        return "/chatRoom/" + chatRoomId;
    }
}
