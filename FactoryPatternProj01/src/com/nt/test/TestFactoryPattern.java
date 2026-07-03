package com.nt.test;

import com.nt.comp.Bike;
import com.nt.factory.BikeFactory;

public class TestFactoryPattern {
public static void main(String[] args) {
	Bike b=BikeFactory.returnBike("electric");
	b.drive();
	System.out.println("==========================");
	
	Bike pb=BikeFactory.returnBike("petrol");
	pb.drive();
	System.out.println("================================");
	
	Bike sb=BikeFactory.returnBike("standard");
	sb.drive();
	System.out.println("====================================");
	
	Bike eb=BikeFactory.returnBike("abcd"); // exception
	eb.drive();
}
}
