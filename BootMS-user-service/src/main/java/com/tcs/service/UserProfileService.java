package com.tcs.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tcs.Entity.UserProfile;
import com.tcs.dto.CreateProfileRequest;
import com.tcs.dto.ProfileResponse;
import com.tcs.dto.UpdateProfileRequest;
import com.tcs.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService implements IUserProfileService{

	private final UserProfileRepository repo;
	
	@Override
	public ProfileResponse createProfile(String authUsername, CreateProfileRequest req) throws Exception {
		if(repo.existsByAuthUsername(authUsername)) {
			throw new RuntimeException("username allready exists");
		}
		UserProfile profile=new UserProfile();
		BeanUtils.copyProperties(req, profile);
		profile.setAuthUsername(authUsername);
		
		UserProfile saved=repo.save(profile);
		return toResponse(saved);
	}

	@Override
	public ProfileResponse getProfileByUsername(String authUsername) throws Exception {
		UserProfile profile=repo.findByAuthUsername(authUsername)
				.orElseThrow(()->new RuntimeException("User does not Exists"));
		
		return toResponse(profile);
	}

	@Override
	public ProfileResponse updateProfile(String authUsername, UpdateProfileRequest req) throws Exception {
		UserProfile profile=repo.findByAuthUsername(authUsername)
				.orElseThrow(()->new RuntimeException("Profile not found."));
		
		copyNonNullProperties(req, profile);
		
		UserProfile updated=repo.save(profile);
		
		return toResponse(updated);
		
		
	}

	@Override
	public String deleteProfile(String authUsername) throws Exception {
		UserProfile profile=repo.findByAuthUsername(authUsername)
				.orElseThrow(()->new RuntimeException("Profile not found."));
		repo.delete(profile);
		return "User deleted with id "+profile.getId();
	}
	
	
	private ProfileResponse toResponse(UserProfile profile) {
		ProfileResponse response=new ProfileResponse();
		BeanUtils.copyProperties(profile, response);
		return response;
	}
	
	 private void copyNonNullProperties(UpdateProfileRequest request, UserProfile profile) {
	        if (request.getFullName() != null) profile.setFullname(request.getFullName());
	        if (request.getEmail() != null) profile.setEmail(request.getEmail());
	        if (request.getPhone() != null) profile.setPhone(request.getPhone());
	        if (request.getAddress() != null) profile.setAddress(request.getAddress());
	        if (request.getDateOfBirth() != null) profile.setDateOfBirth(request.getDateOfBirth());
	        if (request.getGender() != null) profile.setGender(request.getGender());
	    }

}
