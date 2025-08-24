package com.ukm.ssgb.service;

import com.ukm.ssgb.type.LocalCacheType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    public Optional<String> getString(LocalCacheType cacheType, String key) {
        Cache cache = Objects.requireNonNull(cacheManager.getCache(cacheType.getCacheName()));
        return Optional.ofNullable(cache.get(cacheType.getCacheKey(key), String.class));
    }

    public void setString(LocalCacheType cacheType, String key, String value) {
        Cache cache = Objects.requireNonNull(cacheManager.getCache(cacheType.getCacheName()));
        cache.put(cacheType.getCacheKey(key), value);
    }

    public void delete(LocalCacheType cacheType, String key) {
        Cache cache = Objects.requireNonNull(cacheManager.getCache(cacheType.getCacheName()));
        cache.evict(cacheType.getCacheKey(key));
    }
}