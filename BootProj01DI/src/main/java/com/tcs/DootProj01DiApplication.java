package com.tcs;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.tcs.sbeans.Greet;

@SpringBootApplication
public class DootProj01DiApplication {

	@Bean
	public LocalDateTime createDateTime() {
		return LocalDateTime.now();
	}
	
	public static void main(String[] args) {
		
		
		ApplicationContext ctx=SpringApplication.run(DootProj01DiApplication.class, args);
		
		Greet greet=ctx.getBean(Greet.class);
		
		greet.displayGreet("Param");
	}

}
