package com.param.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor 
public class RateLimitController {

    private final StringRedisTemplate redisTemplate;

    private static final int LIMIT=5; // 5 REQUEST ALLOWED IN 60 SECOND
    private static final long WINDOW_SECOND=60; // TIME PER 60 SECOND

    @GetMapping("/limited-resource")
    public String access(@RequestParam String userId){
        String key="retelimit:"+userId;
        long now=Instant.now().getEpochSecond();
        long windowStart=now-WINDOW_SECOND;

        // 1. Drop anything outside the window
        redisTemplate.opsForZSet().removeRangeByScore(key, 0, windowStart);

        // 2. Count what's left inside the window
        Long countWindow=redisTemplate.opsForZSet().zCard(key);

        if(countWindow!=null && countWindow>=LIMIT) {
            return "429 To many request Try again after some time";
        }
        String requestId=now+"-"+UUID.randomUUID();
        redisTemplate.opsForZSet().add(key, requestId, now);
        redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECOND));


        return "Request allowed";
    }



}
