package com.phitrading.pricing.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // Use simple in-memory cache with predefined cache names
        return new ConcurrentMapCacheManager("instrumentPrices");
    }
}
