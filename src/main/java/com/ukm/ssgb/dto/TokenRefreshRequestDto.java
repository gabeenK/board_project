package com.ukm.ssgb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TokenRefreshRequestDto {
    @NotBlank
    private String refreshToken;
}
