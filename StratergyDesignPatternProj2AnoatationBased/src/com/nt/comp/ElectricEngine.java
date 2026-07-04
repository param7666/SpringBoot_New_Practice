package com.nt.comp;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("el")
@Lazy(true)
public final class ElectricEngine implements Engine{

	@Override
	public void engine() {
		System.out.println("ElectricEngine.engine()");
	}

}
