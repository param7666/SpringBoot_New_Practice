package com.nt.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nt.sbeans.DisplayTime;

public class Main {
public static void main(String[] args) {
	ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("com/nt/cfgs/ApplicationContext.xml");
	
	DisplayTime t=(DisplayTime) ctx.getBean("dt");
	t.displayTime();
}
}
