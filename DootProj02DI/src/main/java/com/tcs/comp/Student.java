package com.tcs.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Student {

	@Autowired
	@Qualifier("python")
	private ICourseMaterial material;
	
	
	public void doStudy() {
		material.useSubject();
	}
}
