package com.example.client.config;

import org.springframework.context.annotation.Bean;

public class RedisConfig {

    @Bean
    public RedisConfig redisConfig() {
        return new RedisConfig();
    }
}
