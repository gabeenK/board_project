package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.RegionNameCode;
import com.ukm.ssgb.model.Region;
import com.ukm.ssgb.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class RegionNameFiller {

    private final RegionRepository regionRepository;

    public void fill(RegionNameCode dto) {
        if (dto == null || isNull(dto.getRegionId())) {
            return;
        }

        Region region = regionRepository.findById(dto.getRegionId()).orElseThrow();
        dto.setRegionName(region.getRegionName());
    }
}