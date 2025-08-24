package com.ukm.ssgb.decorator.code;

import com.ukm.ssgb.dto.chat.SenderDto;

public interface ChatSenderCode {
    Long getUserId();

    void setSender(SenderDto sender);
}
