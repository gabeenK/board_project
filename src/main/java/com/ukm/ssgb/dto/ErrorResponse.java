package com.ukm.ssgb.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final ErrorDto error;

    public ErrorResponse(int code, String message) {
        this.error = ErrorDto.builder()
                .code(code)
                .message(message)
                .build();
    }
}
