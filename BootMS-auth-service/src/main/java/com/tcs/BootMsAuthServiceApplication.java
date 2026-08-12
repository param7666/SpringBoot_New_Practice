package com.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BootMsAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootMsAuthServiceApplication.class, args);
		System.out.println("BootMsAuthServiceApplication started...");
		
	}

}
