package com.ukm.ssgb.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum FolderType {
    BUSINESS_REGISTRATION("businessRegistration"),
    PROFILE("profile"),
    BOARD("board"),
    COMMENT("comment"),
    CHAT("chat"),
    EVENT("event");

    private final String url;

    public String getUrl() {
        return url + "/";
    }
}
