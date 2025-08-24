package com.ukm.ssgb.dto.myPage;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordMailCheckRequestDto {

    @NotBlank
    private String certNo;
}
