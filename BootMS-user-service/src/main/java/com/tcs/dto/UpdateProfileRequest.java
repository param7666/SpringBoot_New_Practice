package com.tcs.dto;

import java.time.LocalDate;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProfileRequest {

	@Nullable
	private String fullName;
	
	@Nullable
	private String email;
	
	@Nullable
	private Long phone;
	
	@Nullable
	private String address;
	
	@Nullable
	private LocalDate dateOfBirth;
	
	@Nullable
	private String gender;
}
