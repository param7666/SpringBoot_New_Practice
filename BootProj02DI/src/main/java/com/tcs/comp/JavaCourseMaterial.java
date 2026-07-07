package com.tcs.comp;

import org.springframework.stereotype.Component;

@Component("java")
public class JavaCourseMaterial implements ICourseMaterial{

	@Override
	public void useSubject() {
		// TODO Auto-generated method stub
		System.out.println("JavaCourseMaterial.useSubject()");
		System.out.println("Reading Java Books:: ");
	}

}
