package com.ukm.ssgb.decorator.filler;

import com.ukm.ssgb.decorator.code.BusinessRegistrationCode;
import com.ukm.ssgb.dto.BusinessRegistrationDto;
import com.ukm.ssgb.dto.FileDto;
import com.ukm.ssgb.service.FileService;
import com.ukm.ssgb.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessRegistrationFiller {

    private final FileService fileService;
    private final S3FileService s3FileService;

    public void fill(BusinessRegistrationCode dto) {
        if (dto == null) {
            return;
        }

        if (dto.getBusinessFileId() == null) {
            dto.setBusinessRegistration(BusinessRegistrationDto.empty());
            return;
        }

        FileDto fileDto = fileService.getFile(dto.getBusinessFileId());
        BusinessRegistrationDto businessRegistrationDto = BusinessRegistrationDto.of(fileDto);
        businessRegistrationDto.setFilePath(s3FileService.download(businessRegistrationDto.getFilePath()));

        dto.setBusinessRegistration(businessRegistrationDto);
    }
}