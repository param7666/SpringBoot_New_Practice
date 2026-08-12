package com.tcs.service;

import com.tcs.dto.CreateProfileRequest;
import com.tcs.dto.ProfileResponse;
import com.tcs.dto.UpdateProfileRequest;

public interface IUserProfileService {

	public ProfileResponse createProfile(String authUsername,CreateProfileRequest req)throws Exception;
	
	public ProfileResponse getProfileByUsername(String authUsername)throws Exception;
	
	public ProfileResponse updateProfile(String authUsername,UpdateProfileRequest req) throws Exception;
	
	public String deleteProfile(String authUsername) throws Exception;
}
