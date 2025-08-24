package com.ukm.ssgb.service;

import com.ukm.ssgb.model.ChatRoom;
import com.ukm.ssgb.model.ChatUser;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.repository.ChatRoomRepository;
import com.ukm.ssgb.repository.ChatUserRepository;
import com.ukm.ssgb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatRoom createChatRoom(long ownUserId, Long otherUserId) {
        User otherUser = userRepository.findById(otherUserId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(otherUser.getNickname()));

        Long chatRoomId = chatRoom.getChatRoomId();
        Stream.of(ownUserId, otherUserId)
                .map(userId -> ChatUser.builder()
                        .chatRoomId(chatRoomId)
                        .userId(userId)
                        .build())
                .forEach(chatUserRepository::save);

        return chatRoom;
    }

    public List<ChatRoom> getChatRoomByUserId(long userId) {
        List<Long> chatRoomIds = chatUserRepository.findByUserIdCreatedAtDesc(userId)
                .stream()
                .map(ChatUser::getChatRoomId)
                .toList();

        if (chatRoomIds.isEmpty()) {
            return emptyList();
        }

        return chatRoomRepository.findByChatRoomIdInModifiedAtDesc(chatRoomIds);
    }

    public String getChatRoomName(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow().getChatRoomName();
    }

    public String getReceiverEmail(Long chatRoomId, long ownUserId) {
        Long receiverUserId = chatUserRepository.findByChatRoomIdAndUserIdNot(chatRoomId, ownUserId)
                .map(ChatUser::getUserId)
                .orElseThrow();
        return userRepository.findById(receiverUserId)
                .map(User::getEmail)
                .orElseThrow();
    }
}
