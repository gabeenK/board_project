package com.ukm.ssgb.dto.register;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @AssertTrue
    private boolean agreedService;

    public void validate() {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException();
        }
    }
}
