package com.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tcs.sbeans.Hotel;

@SpringBootApplication
public class BootProj05ValueAnnotationEsplApplication {

	public static void main(String[] args) {
		ApplicationContext ctx=
		SpringApplication.run(BootProj05ValueAnnotationEsplApplication.class, args);
		
		
			Hotel hotel=ctx.getBean(Hotel.class);
			System.out.println(hotel);
		
	}

}
