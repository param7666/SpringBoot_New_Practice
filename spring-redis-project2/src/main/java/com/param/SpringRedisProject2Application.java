package com.param;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching 
public class SpringRedisProject2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisProject2Application.class, args);
	}

}
