package com.ukm.ssgb.dto.register;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CertMailRequestDto {

    @NotBlank
    private String email;
}
