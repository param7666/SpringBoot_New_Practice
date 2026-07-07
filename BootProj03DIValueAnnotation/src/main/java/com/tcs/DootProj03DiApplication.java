package com.tcs;

import com.tcs.comp.BF;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class DootProj03DiApplication {

	public static void main(String[] args) {
		ApplicationContext ctx= SpringApplication.run(DootProj03DiApplication.class, args);
		
		BF bf=ctx.getBean(BF.class);
		bf.go();
	}

}
