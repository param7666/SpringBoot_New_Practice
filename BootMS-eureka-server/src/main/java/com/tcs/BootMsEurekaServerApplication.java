package com.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BootMsEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootMsEurekaServerApplication.class, args);
		System.out.println("BootMsEurekaServerApplication Started...");
	}

}
