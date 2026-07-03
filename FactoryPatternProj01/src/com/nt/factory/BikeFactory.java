package com.nt.factory;

import com.nt.comp.Bike;
import com.nt.comp.ElectricBike;
import com.nt.comp.PetrolBike;
import com.nt.comp.StandardBike;

public class BikeFactory {

	public static Bike returnBike(String type) {
		if(type.equalsIgnoreCase("electric")) return new ElectricBike();
		else if(type.equalsIgnoreCase("petrol")) return new PetrolBike();
		else if(type.equalsIgnoreCase("standard")) return new StandardBike();
		else throw new IllegalArgumentException("Invalid Bike type");
	}
}
