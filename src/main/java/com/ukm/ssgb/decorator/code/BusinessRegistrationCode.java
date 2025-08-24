package com.ukm.ssgb.decorator.code;

import com.ukm.ssgb.dto.BusinessRegistrationDto;

public interface BusinessRegistrationCode {
    Long getBusinessFileId();

    void setBusinessRegistration(BusinessRegistrationDto businessRegistrationDto);
}
