package com.tcs.sbeans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Menu {

	@Value("${menu.dosarate}")
	private Float dosaprice;
	@Value("${menu.poharate}")
	private Float pohaPrice;
	@Value("${menu.idlyrate}")
	private Float idlyPrice;
	@Value("${menu.vprate}")
	private Float vpPrice;
	
}
