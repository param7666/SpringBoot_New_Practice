package com.nt.main;

import com.nt.EngineFactory.EngineFactory;
import com.nt.EngineFactory.GetEngine;

public class Main {

	public static void main(String[] args) {
		GetEngine gt=EngineFactory.getInstance("electric");
		gt.applyEngine();
	}
}
