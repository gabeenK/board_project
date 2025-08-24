package com.ukm.ssgb.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ROLE_USER("앱사용자"),
    ROLE_ADMIN("관리자"),
    ROLE_PENDING_USER("앱사용자"),
    ;

    private final String displayName;
}
