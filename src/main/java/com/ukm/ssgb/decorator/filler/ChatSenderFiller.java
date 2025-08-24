package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.ChatSenderCode;
import com.ukm.ssgb.dto.chat.SenderDto;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatSenderFiller {

    private final UserRepository userRepository;

    public void fill(ChatSenderCode dto) {
        if (dto == null) {
            return;
        }

        User user = userRepository.findById(dto.getUserId()).orElseThrow();

        dto.setSender(new SenderDto(user));
    }
}