package com.tcs.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateProductRequest {

	@Nullable
	private String name;
	@Nullable
	private String description;
	private Double price;
	private Integer stock;
	@Nullable
	private String category;
}
