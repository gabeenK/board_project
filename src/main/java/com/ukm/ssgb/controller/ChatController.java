package com.ukm.ssgb.controller;

import com.ukm.ssgb.dto.chat.ChatCreateRequestDto;
import com.ukm.ssgb.dto.chat.ChatIdDto;
import com.ukm.ssgb.facade.ChatFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatFacade chatFacade;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChatIdDto> createChat(@Valid @ModelAttribute ChatCreateRequestDto requestDto) {
        ChatIdDto result = chatFacade.createChat(requestDto);
        return ResponseEntity.ok(result);
    }
}
