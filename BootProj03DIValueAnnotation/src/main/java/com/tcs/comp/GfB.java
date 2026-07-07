package com.tcs.comp;

import org.springframework.stereotype.Component;

@Component("gfb")
public class GfB implements IGFS{

	@Override
	public void gotoOyo() {
		// TODO Auto-generated method stub
		System.out.println("GfB.gotoOyo()");
		System.out.println("BF went Oyo with GF B");
	}

}
