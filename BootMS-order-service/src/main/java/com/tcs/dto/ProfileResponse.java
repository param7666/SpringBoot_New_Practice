package com.tcs.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

	private Long id;
	
	private String authUsername;
	
	private String fullname;
	
	private String email;
	
	private Long phone;
	
	private String address;
	
	private LocalDate dateOfBirth;
	
	private String gender;
	
	private LocalDateTime createdAt;
}
