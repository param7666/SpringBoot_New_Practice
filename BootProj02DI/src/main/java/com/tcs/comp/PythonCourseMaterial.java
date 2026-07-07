package com.tcs.comp;

import org.springframework.stereotype.Component;

@Component("python")
public class PythonCourseMaterial implements ICourseMaterial{

	@Override
	public void useSubject() {
		// TODO Auto-generated method stub
		System.out.println("PythonCourseMaterial.useSubject()");
		System.out.println("Reading Python Books");
	}

}
