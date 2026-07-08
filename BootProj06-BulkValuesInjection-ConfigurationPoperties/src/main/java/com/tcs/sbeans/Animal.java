package com.tcs.sbeans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;

import lombok.Setter;
import lombok.ToString;

@Controller
@Setter
@ToString
@ConfigurationProperties(prefix = "wild.animal") // configuration properties
// help to inject bulk values but rule is .properties prefix and varible names should be same

public class Animal {
	
	private Integer id;
	
	private String name;
	
	private String jungle;
	
	private Double price;
	
	private String food;


}
