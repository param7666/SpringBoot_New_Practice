package com.tcs.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldSchema {

	private String name;
	
	private String type;
	
	private List<String> constraints=new ArrayList<String>();
}
