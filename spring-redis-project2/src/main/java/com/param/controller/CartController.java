package com.param.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/cart")
@RequiredArgsConstructor 
public class CartController {

    private final StringRedisTemplate redisTemplate;

    private String key(String  userId) {
        return "cart"+userId;
    }

    // ADD: works for new AND existing products
    @PostMapping("/{userId}/add")
    public String addItem(@PathVariable String userId, @RequestParam String productId, @RequestParam(defaultValue = "1") Long qty ){

        redisTemplate.opsForHash().increment(key(userId), productId, qty);
        redisTemplate.expire(key(userId), Duration.ofDays(1)); // cart auto-expires after 7 days
        return "added "+qty+" of "+productId;
    }

    // VIEW: whole cart
    @GetMapping("/{userId}")
    public Map<Object,Object> viewCart(@PathVariable String userId){
        return redisTemplate.opsForHash().entries(key(userId));
    }

    // REMOVE: one product completely
    @DeleteMapping("/{userId}/remove")
    public String removeItem(@PathVariable String userId, @RequestParam String productId){
        redisTemplate.opsForHash().delete(key(userId), productId);
        return "Cart Removed...";
    }

    // DECREASE: the "-" button
    @PostMapping("/{userId}/decrease")
    public String descrease(@PathVariable String userId,@RequestParam String productId){
        Long newQty=redisTemplate.opsForHash().increment(key(userId), productId, -1);
        if(newQty!=null && newQty<=0){
            redisTemplate.opsForHash().delete(key(userId), productId);
            return productId+" removed from cart";
        }
        return productId+" quantity is now "+newQty;
    }

    // CLEAR: delete the whole cart
    @DeleteMapping ("/{userId}/clear")
    public String clearAllCart(@PathVariable String userId){
        redisTemplate.opsForHash().delete(key(userId));
        return "Cart deleted..";
    }

    @GetMapping("/{userId}/count")
    public Long numberOfUniqueCart(@PathVariable String userId){
        return redisTemplate.opsForHash().size(key(userId));
    }

    @GetMapping("/{userId}/item")
    public Long productQty(@PathVariable String userId,@RequestParam  String productId){
        Object qty=redisTemplate.opsForHash().get(key(userId),productId);
        return qty==null? 0 : Long.parseLong(qty.toString());
    }

}
