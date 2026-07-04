package com.tcs.comp;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("dotnet")
@Primary
public class DotNetCourseMaterial implements ICourseMaterial{

	@Override
	public void useSubject() {
		// TODO Auto-generated method stub
		System.out.println("DotNetCourseMaterial.useSubject()");
		System.out.println("Reading DotNet Books:: ");
	}

}
