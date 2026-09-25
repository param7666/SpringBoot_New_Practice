package com.tcs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class RedisConfig {

	@Bean
	public RedisScript<Long> likeScript() {
		System.out.println("RedisConfig.likeScript()");
		
		DefaultRedisScript<Long> script=new DefaultRedisScript<Long>();
		script.setLocation(new ClassPathResource("scripts/like.lua"));
		script.setResultType(Long.class);
		
		System.out.println("RedisConfig.likeScript():: script "+script);
		return script;
	}
}
