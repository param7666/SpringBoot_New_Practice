package com.param.controller;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class RateLimitControllerUsingScript {

    private final StringRedisTemplate redisTemplate;

    private static final int LIMIT=5;
    private static final long WINDOW_SECONDS=60;

    private static final String SCRIPT =
        "redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, ARGV[1]) " +
        "local count = redis.call('ZCARD', KEYS[1]) " +
        "if count < tonumber(ARGV[2]) then " +
        "   redis.call('ZADD', KEYS[1], ARGV[3], ARGV[4]) " +
        "   redis.call('EXPIRE', KEYS[1], ARGV[5]) " +
        "   return 1 " +
        "else " +
        "   return 0 " +
        "end";


    @GetMapping("/limited-resource")
    public String access(@RequestParam String userId) {
        String key = "ratelimit:" + userId;
        long now = Instant.now().getEpochSecond();
        long windowStart = now - WINDOW_SECONDS;
        String requestId = now + "-" + UUID.randomUUID();

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(SCRIPT, Long.class);

        Long allowed = redisTemplate.execute(
                redisScript,
                Collections.singletonList(key),
                String.valueOf(windowStart),
                String.valueOf(LIMIT),
                String.valueOf(now),
                requestId,
                String.valueOf(WINDOW_SECONDS)
        );

        return (allowed != null && allowed == 1) ? "Request allowed" : "429 Too Many Requests";

    }

}
