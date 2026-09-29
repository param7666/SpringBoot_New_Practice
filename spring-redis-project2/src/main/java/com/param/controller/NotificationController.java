package com.param.controller;

import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications/redis") 
@RequiredArgsConstructor 

public class NotificationController {

    private final StringRedisTemplate redisTemplate;

    private String key(String userId){
        return "notification:"+userId;
    }

      // PRODUCE: add a notification at the tail
    @GetMapping("/{userId}")
    public String add(@PathVariable String userId,@RequestParam String message){
        Long size=redisTemplate.opsForList().rightPush(key(userId), message);
        return "Added. Queue size: "+size;
    }

    // CONSUME: take the oldest one (removes it)
    @GetMapping("/{userId}/next")
    public String next(@PathVariable String userId){
        String message=redisTemplate.opsForList().leftPop(key(userId));
        return message==null ? "No Notification" : message;
    }


    // PEEK: view all without removing
    @GetMapping("/{userId}")
    public List<String> viewAll(@PathVariable String userId){
        return redisTemplate.opsForList().range(key(userId), 0, -1);
    }


    // COUNT
    @GetMapping("/{userId}/count")
    public Long count(@PathVariable String userId) {
        return redisTemplate.opsForList().size(key(userId));
    }

}

