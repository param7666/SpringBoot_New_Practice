package com.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tcs.sbeans.Animal;

@SpringBootApplication
public class BootProj06BulkValuesInjectionConfigurationPopertiesApplication {

	public static void main(String[] args) {
		ApplicationContext context=
		SpringApplication.run(BootProj06BulkValuesInjectionConfigurationPopertiesApplication.class, args);
		
		Animal animal=context.getBean(Animal.class);
		
		System.out.println(animal);
	}

}
