package com.tcs.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProfileRequest {
	
	@NotBlank(message = "Full Name is Required.")
	private String fullName;
	
	@NotBlank(message ="Email is Required.")
	@Email
	private String email;
	
	//@NotBlank(message = "Mobile Number is Required.")
	//@Size(min = 10,max = 10)
	private long phone;
	
	@NotBlank(message = "address is Required.")
	private String address;
	
	//@NotBlank(message = "date Of Birth is Required.")
	private LocalDate dateOfBirth;
	
	@NotBlank(message = "Gender is Required.")
	private String gender;

}
