package com.tcs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.Entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

	Optional<UserProfile> findByAuthUsername(String authUsername);
	
	boolean existsByAuthUsername(String authUsername);
}
