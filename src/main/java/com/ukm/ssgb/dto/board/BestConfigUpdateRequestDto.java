package com.ukm.ssgb.dto.board;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BestConfigUpdateRequestDto {

    @NotNull
    @Positive
    private Integer bestSize;
}