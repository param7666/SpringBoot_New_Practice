package com.nt.comp;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("pt")
@Lazy(true)
//@Primary
public final class PetrolEngine implements Engine{

	@Override
	public void engine() {
		System.out.println("PetrolEngine.engine()");
	}

	
}
