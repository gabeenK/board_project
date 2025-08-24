package com.ukm.ssgb.dto.password;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordCertMailRequestDto {

    @NotBlank
    private String email;
}
