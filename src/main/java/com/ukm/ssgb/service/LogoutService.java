package com.ukm.ssgb.service;

import com.ukm.ssgb.type.LocalCacheType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ukm.ssgb.util.TokenUtil.getIdWithThrow;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final CacheService cacheService;

    public void logout() {
        cacheService.delete(LocalCacheType.REFRESH_TOKEN, String.valueOf(getIdWithThrow()));
    }
}
