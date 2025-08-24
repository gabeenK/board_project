package com.ukm.ssgb.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ukm.ssgb.type.LocalCacheType;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public List<Pair<String, Cache<Object, Object>>> caches() {
        return Arrays.stream(LocalCacheType.values())
                .map(localCacheType -> Pair.of(localCacheType.getCacheName(), Caffeine.newBuilder()
                        .maximumSize(localCacheType.getMaximumSize())
                        .expireAfterWrite(localCacheType.getExpiredAfterWrite(), localCacheType.getTimeUnit())
                        .build()))
                .toList();
    }

    @Bean
    public CacheManager caffeineCacheManager(List<Pair<String, Cache<Object, Object>>> caches) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caches.forEach(cache -> caffeineCacheManager.registerCustomCache(cache.getKey(), cache.getValue()));
        return caffeineCacheManager;
    }
}
