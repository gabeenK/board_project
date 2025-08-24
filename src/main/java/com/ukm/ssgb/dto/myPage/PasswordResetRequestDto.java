package com.ukm.ssgb.dto.myPage;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordResetRequestDto {

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}
