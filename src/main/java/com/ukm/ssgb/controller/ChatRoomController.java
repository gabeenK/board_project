package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.chatroom.ChatRoomCreateRequestDto;
import com.ukm.ssgb.dto.chatroom.ChatRoomDetailDto;
import com.ukm.ssgb.dto.chatroom.ChatRoomIdDto;
import com.ukm.ssgb.dto.chatroom.ChatRoomsDto;
import com.ukm.ssgb.dto.page.PageRequestDto;
import com.ukm.ssgb.facade.ChatRoomFacade;
import com.ukm.ssgb.util.TokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatRoom")
public class ChatRoomController {

    private final ChatRoomFacade chatRoomFacade;

    @PostMapping
    public ResponseEntity<ChatRoomIdDto> createChatRoom(@Valid @RequestBody ChatRoomCreateRequestDto requestDto) {
        ChatRoomIdDto result = chatRoomFacade.createChatRoom(TokenUtil.getIdWithThrow(), requestDto.getOtherUserId());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ChatRoomsDto> getChatRoom() {
        ChatRoomsDto result = chatRoomFacade.getChatRoom(TokenUtil.getIdWithThrow());
        return ResponseEntity.ok(result);
    }

    @GetMapping("{chatRoomId}")
    public ResponseEntity<ChatRoomDetailDto> getChatRoomDetails(@PathVariable("chatRoomId") Long chatRoomId,
                                                                @Valid PageRequestDto pageRequestDto) {
        ChatRoomDetailDto result = chatRoomFacade.getChatRoomDetails(chatRoomId, pageRequestDto.getPageRequest());
        return ResponseEntity.ok(result);
    }
}
