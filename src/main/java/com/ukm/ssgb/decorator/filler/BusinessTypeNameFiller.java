package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.BusinessTypeNameCode;
import com.ukm.ssgb.model.BusinessType;
import com.ukm.ssgb.repository.BusinessTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class BusinessTypeNameFiller {

    private final BusinessTypeRepository businessTypeRepository;

    public void fill(BusinessTypeNameCode dto) {
        if (dto == null || isNull(dto.getBusinessTypeId())) {
            return;
        }

        BusinessType businessType = businessTypeRepository.findById(dto.getBusinessTypeId()).orElseThrow();
        dto.setBusinessTypeName(businessType.getBusinessTypeName());
    }
}