package com.param;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching 
public class SpringRedisProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisProjectApplication.class, args);
	}

}
