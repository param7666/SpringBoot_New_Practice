package com.param.utils;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class RateLimiter {

    private final StringRedisTemplate redisTemplate;

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

    private final DefaultRedisScript<Long> redisScript=new DefaultRedisScript<>(SCRIPT, Long.class);

    public boolean isAllowed(String key,int limit, Long windowSeconds) {
        Long now=Instant.now().getEpochSecond();
        Long windowStart=now-windowSeconds;
        String requestId=now+"-"+UUID.randomUUID();

        Long allowed = redisTemplate.execute(
        redisScript,
        Collections.singletonList(key),
        String.valueOf(windowStart),   // ARGV[1]
        String.valueOf(limit),         // ARGV[2]
        String.valueOf(now),           // ARGV[3]
        requestId,                     // ARGV[4]
        String.valueOf(windowSeconds)     // ARGV[5]
);
        return allowed != null && allowed == 1;
    }
}
