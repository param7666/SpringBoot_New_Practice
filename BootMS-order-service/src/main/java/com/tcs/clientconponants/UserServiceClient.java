package com.tcs.clientconponants;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcs.dto.ProfileResponse;

@FeignClient(name = "user-service")
public interface UserServiceClient {

	@GetMapping("/api/users/{authUsername}")
	ProfileResponse getProfile(@PathVariable String authUsername);
	
	
}
