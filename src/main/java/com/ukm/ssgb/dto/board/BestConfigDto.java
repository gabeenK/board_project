package com.ukm.ssgb.dto.board;

import com.ukm.ssgb.model.BestConfig;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BestConfigDto {
    private int bestSize;
    private boolean active;

    public static BestConfigDto toDto(BestConfig bestConfig) {
        return BestConfigDto.builder()
                .bestSize(bestConfig.getBestSize())
                .active(bestConfig.isActive())
                .build();
    }
}
