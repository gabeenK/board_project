package com.ukm.ssgb.dto.usermanagement;

import com.ukm.ssgb.decorator.code.BusinessRegistrationCode;
import com.ukm.ssgb.decorator.code.BusinessTypeNameCode;
import com.ukm.ssgb.decorator.code.RegionNameCode;
import com.ukm.ssgb.dto.BusinessRegistrationDto;
import com.ukm.ssgb.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class GetUserDto implements BusinessTypeNameCode, RegionNameCode, BusinessRegistrationCode {

    private final Long userId;
    private final String email;
    private final String loginTypeName;
    private final String roleName;
    private final Long businessTypeId;
    private final Long regionId;
    private final Long businessFileId;
    private final LocalDateTime createdAt;
    private final boolean approved;

    @Setter
    private String businessTypeName;
    @Setter
    private String regionName;
    @Setter
    private BusinessRegistrationDto businessRegistration;

    public GetUserDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.loginTypeName = user.getLoginType().getDisplayName();
        this.roleName = user.getRole().getDisplayName();
        this.businessTypeId = user.getBusinessTypeId();
        this.businessFileId = user.getBusinessFileId();
        this.regionId = user.getRegionId();
        this.createdAt = user.getCreatedAt();
        this.approved = user.getApproved();
    }
}
