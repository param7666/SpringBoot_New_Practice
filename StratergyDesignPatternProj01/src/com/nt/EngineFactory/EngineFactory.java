package com.nt.EngineFactory;

import com.nt.comp.ElectricEngine;
import com.nt.comp.Engine;
import com.nt.comp.PetrolEngine;

public class EngineFactory {

	public static GetEngine getInstance(String type) {
		Engine eng=null;
		if(type.equalsIgnoreCase("petrol")) eng=new PetrolEngine();
		else if(type.equalsIgnoreCase("electric")) eng=new ElectricEngine();
		else throw new IllegalArgumentException("Invalid Engine");
		GetEngine gt=new GetEngine();
		gt.setEngine(eng);
		return gt;
	}
}
