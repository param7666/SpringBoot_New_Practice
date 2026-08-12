package com.tcs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.CreateProfileRequest;
import com.tcs.dto.ProfileResponse;
import com.tcs.dto.UpdateProfileRequest;
import com.tcs.service.IUserProfileService;

import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

	private final IUserProfileService service;
	
	@PostMapping
	public ResponseEntity<?> createProfile(
			@RequestHeader("X-Auth-Username")String authUsername,
			@Valid @RequestBody CreateProfileRequest req) {
		
		try {
			ProfileResponse response=service.createProfile(authUsername, req);
			return new ResponseEntity<>(response,HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> getMyProfile(@RequestHeader("X-Auth-Username") String username) {
		try {
			ProfileResponse response=service.getProfileByUsername(username);
			return new ResponseEntity<ProfileResponse>(response,HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);
		}
	}
	
	@PutMapping("/me")
	public ResponseEntity<?> getMyProfile(
			@RequestHeader("X-Auth-Username") String username, 
			@RequestBody UpdateProfileRequest req) {
		
		try {
			
			ProfileResponse response=service.updateProfile(username, req);
			return new ResponseEntity<ProfileResponse>(response,HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/me")
	public String deleteProfile(@RequestHeader("X-Auth-Username") String authUsername) {
		try {
			return service.deleteProfile(authUsername);
		} catch(Exception e) {
			return e.getMessage();
		}
	}
	
	 @GetMapping("/{authUsername}")
	    public ResponseEntity<?> getProfileByUsername(
	            @PathVariable String authUsername) {

	       try {
	    	   ProfileResponse response = service.getProfileByUsername(authUsername);
		        return ResponseEntity.ok(response);
	       }catch(Exception e) {
	    	   return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	       }
	    }
}
