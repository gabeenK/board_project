package com.ukm.ssgb.service;

import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.Chat;
import com.ukm.ssgb.model.FileEntity;
import com.ukm.ssgb.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ukm.ssgb.type.FolderType.CHAT;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final FileService fileService;

    @Transactional
    public Chat createChat(Long chatRoomId, Long userId, String content, List<MultipartFile> attachments) {
        boolean hasAttachments = !attachments.isEmpty();
        if (hasAttachments) {
            return createAttachmentsChat(chatRoomId, userId, attachments);
        }

        return createContentChat(chatRoomId, userId, content);
    }

    private Chat createContentChat(Long chatRoomId, Long userId, String content) {
        if (StringUtils.isEmpty(content)) {
            throw new ValidationException("메시지를 작성해 주세요!");
        }

        Chat chat = Chat.contentChat(chatRoomId, userId, content);
        return chatRepository.save(chat);
    }

    private Chat createAttachmentsChat(Long chatRoomId, long userId, List<MultipartFile> attachments) {
        List<FileEntity> fileEntities = fileService.saveAll(CHAT, attachments);
        Chat chat = Chat.attachmentsChat(chatRoomId, userId, fileEntities);
        return chatRepository.save(chat);
    }

    public Chat getLastChat(Long chatRoomId) {
        return chatRepository.findFirstByChatRoomIdOrderByCreatedAtDesc(chatRoomId).orElseThrow();
    }
}
