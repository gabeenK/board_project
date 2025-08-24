package com.ukm.ssgb.service;

import com.ukm.ssgb.dto.combo.BusinessTypeDto;
import com.ukm.ssgb.dto.combo.BusinessTypesDto;
import com.ukm.ssgb.dto.combo.RegionDto;
import com.ukm.ssgb.dto.combo.RegionsDto;
import com.ukm.ssgb.repository.BusinessTypeRepository;
import com.ukm.ssgb.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessTypeService {

    private final BusinessTypeRepository businessTypeRepository;

    public BusinessTypesDto getBusinessTypes() {
        List<BusinessTypeDto> businessTypes = businessTypeRepository.findAll()
                .stream()
                .map(businessType -> new BusinessTypeDto(businessType.getBusinessTypeId(), businessType.getBusinessTypeName()))
                .toList();
        return new BusinessTypesDto(businessTypes);
    }
}
