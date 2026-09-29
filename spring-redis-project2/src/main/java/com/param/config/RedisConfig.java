package com.param.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

@Configuration 
public class RedisConfig {


    // Redis bean with Serializer

    @Bean 
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(RedisSerializer.json());

        // template.setKeySerializer(RedisSerializer.string());
        // template.setHashKeySerializer(RedisSerializer.string());

        // RedisSerializer<Object> jsonSerializer=GenericJacksonJsonRedisSerializer.
        //                         builder().enableDefaultTyping(LaissezFaireSubTypeValidator.instance).build();

        // template.setValueSerializer(jsonSerializer);
        // template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }


    // redis Configuration for Expiration of cache
    @Bean 
    public RedisCacheConfiguration redisCacheConfiguration(){
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofDays(1))
            .disableCachingNullValues();
    }


}
