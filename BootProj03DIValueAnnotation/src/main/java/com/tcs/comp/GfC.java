package com.tcs.comp;

import org.springframework.stereotype.Component;

@Component("gfc")
public class GfC implements IGFS{



	@Override
	public void gotoOyo() {
		// TODO Auto-generated method stub
		System.out.println("GfC.gotoOyo()");
		System.out.println("BF went Oyo with GF C");
	}

}
