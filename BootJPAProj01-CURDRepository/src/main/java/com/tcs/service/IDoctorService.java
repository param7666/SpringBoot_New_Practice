package com.tcs.service;

import java.util.Optional;

import com.tcs.entity.Doctor;

public interface IDoctorService {

	public String inserDoctor(Doctor d) throws Exception;
	
	public Optional<Doctor> getDoctorById(Long id) throws Exception;
}
