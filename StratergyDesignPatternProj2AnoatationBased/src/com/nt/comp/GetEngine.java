package com.nt.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component
public final class GetEngine {

	@Autowired
	@Qualifier("pt")
	private Engine engine;
	
	public void setEngine(Engine engine) {
		this.engine=engine;
	}
	
	public void applyEngine() {
		engine.engine();
	}
}
