package com.ukm.ssgb.service;

import com.ukm.ssgb.dto.combo.RegionDto;
import com.ukm.ssgb.dto.combo.RegionsDto;
import com.ukm.ssgb.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionsDto getRegions() {
        List<RegionDto> regions = regionRepository.findAll()
                .stream()
                .map(region -> new RegionDto(region.getRegionId(), region.getRegionName()))
                .toList();
        return new RegionsDto(regions);
    }
}
