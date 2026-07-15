package com.tcs.service;

import java.util.Optional;

import com.tcs.entity.Doctor;

public interface IDoctorService {

	public String inserDoctor(Doctor d) throws Exception;
	
	public Optional<Doctor> getDoctorById(Long id) throws Exception;
	
	public Iterable<Doctor> showAllDoctor() throws Exception;
	
	public String RegisterAllDoctor(Iterable<Doctor> allDoc) throws Exception;
	
	public String checkDoctorExit(Long id) throws Exception;
	
	public Long getAllDoctorCount() throws Exception;
	
	public String deleteDoctorById(Long id) throws Exception;
	
	
}
