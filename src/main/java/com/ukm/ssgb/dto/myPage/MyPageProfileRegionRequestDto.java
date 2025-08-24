package com.ukm.ssgb.dto.myPage;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MyPageProfileRegionRequestDto {

    @Positive
    @NotNull
    Long regionId;
}
