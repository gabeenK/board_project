package com.ukm.ssgb.decorator;

import com.ukm.ssgb.decorator.filler.ChatSenderFiller;
import com.ukm.ssgb.decorator.filler.FileNameFiller;
import com.ukm.ssgb.dto.chat.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatDecorator {

    private final ChatSenderFiller chatSenderFiller;
    private final FileNameFiller fileNameFiller;

    public void decorate(ChatDto dto) {
        if (dto == null) {
            return;
        }
        chatSenderFiller.fill(dto);
        fileNameFiller.fill(dto.getAttachments());
    }
}