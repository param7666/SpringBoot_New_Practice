package com.tcs.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableSchema {

	private String tableName;
	private List<FieldSchema> fields=new ArrayList<FieldSchema>();
}
