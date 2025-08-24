package com.ukm.ssgb.dto;

import lombok.Builder;

public record ErrorDto(int code, String message) {
    @Builder
    public ErrorDto {
    }
}
