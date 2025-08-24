package com.ukm.ssgb.dto.usermanagement;

import com.ukm.ssgb.model.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUsersDto {

    private final Long userId;
    private final String email;
    private final String loginTypeName;
    private final boolean existsBusinessFile;
    private final String roleName;
    private final String businessTypeName;
    private final String regionName;
    private final LocalDateTime createdAt;
    private final boolean approved;

    public GetUsersDto(User user, String businessTypeName, String regionName) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.loginTypeName = user.getLoginType().getDisplayName();
        this.existsBusinessFile = user.existsBusinessFile();
        this.roleName = user.getRole().getDisplayName();
        this.businessTypeName = businessTypeName;
        this.regionName = regionName;
        this.createdAt = user.getCreatedAt();
        this.approved = user.getApproved();
    }
}
