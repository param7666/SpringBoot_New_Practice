package com.param.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/redis")
@RequiredArgsConstructor 

public class RedisController {

    private final RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/set") 
    public String setValue(){
        redisTemplate.opsForValue().set("message", "Hello Redis");
        return "Value Stored...";
    }

    @GetMapping ("/get")
    public String getValue(){
        Object value = redisTemplate.opsForValue().get("message");
        return value.toString();
    }

    @GetMapping("/hash/set")
    public String setHash(){

        HashOperations<String,String,String> hashops= redisTemplate.opsForHash();
        hashops.put("user:101", "Name", "Param");
        hashops.put("user:101","Role","Java Developer");
        hashops.put("user:101","City","Hyderabad");
        return "Hash Stored";
    }

    @GetMapping("/hash/get")
    public Object getHashFeild(){
        return redisTemplate.opsForHash().get("user:101", "Name");
    }

    @GetMapping("/hash/getall")
    public Map<Object,Object> getAllFeild(){
        return redisTemplate.opsForHash().entries("user:101");
    }

@GetMapping("/set/add")
public String addToSet() {
    redisTemplate.opsForSet().add("tags:blogpost:1", "spring", "redis", "java", "spring"); // duplicate "spring" ignored
    return "Added";
}

@GetMapping("/set/members")
public Set<Object> getSetMembers() {
    return redisTemplate.opsForSet().members("tags:blogpost:1");
}

@GetMapping("/set/check")
public Boolean isMember() {
    return redisTemplate.opsForSet().isMember("tags:blogpost:1", "redis");
}

@GetMapping("/zset/add")
public String addToZSet() {
    ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
    zSetOps.add("leaderboard", "Param", 850);
    zSetOps.add("leaderboard", "Ravi", 920);
    zSetOps.add("leaderboard", "Sneha", 780);
    return "Added";
}

@GetMapping("/zset/top")
public Set<Object> getTopScorers() {
    // reverseRange = highest score first
    return redisTemplate.opsForZSet().reverseRange("leaderboard", 0, 2); // top 3
}

@GetMapping("/zset/rank")
public Long getRank(String name) {
    return redisTemplate.opsForZSet().reverseRank("leaderboard", name); // 0-indexed position
}


@GetMapping("/list/push")
public String pushList() {
    ListOperations<String, Object> listOps = redisTemplate.opsForList();
    listOps.rightPush("notifications:param", "New message from TCS team");
    listOps.rightPush("notifications:param", "Deployment successful");
    return "Pushed";
}

@GetMapping("/list/get")
public List<Object> getList() {
    return redisTemplate.opsForList().range("notifications:param", 0, -1); // 0 to -1 = full list
}

@GetMapping("/list/pop")
public Object popList() {
    return redisTemplate.opsForList().leftPop("notifications:param"); // classic queue: leftPop + rightPush
}
}
