package com.ukm.ssgb.type;

import com.ukm.ssgb.constant.ServiceConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
public enum LocalCacheType {

    FILE(CacheName.FILE, 60, TimeUnit.MINUTES, 200),
    NICKNAME_COUNT(CacheName.NICKNAME_COUNT, 60, TimeUnit.MINUTES, 200),
    CERT_NO(CacheName.CERT_NO, 10, TimeUnit.MINUTES, 100),
    REFRESH_TOKEN(CacheName.REFRESH_TOKEN, ServiceConstants.CERT_NO_VALIDATE_TIME, TimeUnit.MILLISECONDS, 100);

    private final String cacheName;
    private final long expiredAfterWrite;
    private final TimeUnit timeUnit;
    private final int maximumSize;

    public String getCacheKey(String... keys) {
        if (ArrayUtils.isEmpty(keys)) {
            throw new IllegalArgumentException("keys should not be empty");
        }

        return String.format("%s:%s", cacheName, String.join(":", keys));
    }
}