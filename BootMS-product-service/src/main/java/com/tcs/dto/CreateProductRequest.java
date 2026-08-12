package com.tcs.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

	@NotBlank(message = "Name is Required.")
	private String name;
	@NotBlank(message = "description is Required.")
	private String description;
	private Double price;
	private Integer stock;
	@NotBlank(message = "category is Required.")
	private String category;
}
