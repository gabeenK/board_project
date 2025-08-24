package com.ukm.ssgb.dto.register;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterProfileRequestDto {

    @NotNull
    @Positive
    private Long regionId;

    @NotNull
    @Positive
    private Long businessTypeId;
}
