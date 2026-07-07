package com.tcs.comp;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("gfa")
//@Primary
public class GfA implements IGFS{

	@Override
	public void gotoOyo() {
		// TODO Auto-generated method stub
		System.out.println("GfA.gotoOyo()");
		System.out.println("Bf Went Oyo with GF A");
	}

	

}
