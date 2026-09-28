package com.param.config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.param.entity.Book;

@Configuration
@EnableCaching 
public class RedisConfig {

    // @Bean
    // CommandLineRunner testCacheManager(CacheManager cacheManager) {
    //     return args -> {
    //         System.out.println("CACHE MANAGER = "+ cacheManager.getClass().getName());

    //         System.out.println("CACHE = "+ cacheManager.getCache("books"));
    //     };
    // }



    @Bean 
    public RedisCacheConfiguration redisConfiguration(){
        JacksonJsonRedisSerializer<Book> serializer=new JacksonJsonRedisSerializer<>(Book.class);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

    }




}
