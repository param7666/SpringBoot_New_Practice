package com.nt.EngineFactory;

import com.nt.comp.Engine;

public final class GetEngine {

	private Engine engine;
	
	public void setEngine(Engine engine) {
		this.engine=engine;
	}
	
	public void applyEngine() {
		engine.engine();
	}
}
