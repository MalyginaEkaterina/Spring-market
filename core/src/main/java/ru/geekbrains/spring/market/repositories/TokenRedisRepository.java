package ru.geekbrains.spring.market.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
public class TokenRedisRepository {
    @Autowired
    private RedisTemplate<String, Integer> tokenRedisTemplate;

    public void putToken(String token, Duration duration) {
        tokenRedisTemplate.opsForValue().set("token:" + token, 1, duration);
    }

    public Integer getToken(String token) {
        return tokenRedisTemplate.opsForValue().get("token:" + token);
    }
}
