package com.nt.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nt.EngineFactory.AppConfig;
import com.nt.comp.GetEngine;

public class Main {

	public static void main(String[] args) {
		//GetEngine gt=EngineFactory.getInstance("electric");
		
		AnnotationConfigApplicationContext ctx=
				new AnnotationConfigApplicationContext(AppConfig.class);
		GetEngine gt=ctx.getBean(GetEngine.class);
		
		
		gt.applyEngine();
	}
}
