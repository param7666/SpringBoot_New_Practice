package com.tcs.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.tcs.entity.Doctor;

public interface IDoctorService {

	public Iterable<Doctor> showAllDoctor(Boolean asc,String ...props)throws Exception;
	
	public Page<Doctor> showAllDoctorByPageNo(int pageNo,int pageSize) throws Exception;
	
	public Page<Doctor> showAllDoctorByPageAndSorting(int pageNo,int pageSize,Boolean asc,String ...prop) throws Exception;
	
	public void showAllDoctorPegination(int pageSize) throws Exception;
	
	public List<Doctor> showAllDoctorBySpecialization(String speci) throws Exception;
	
	public List<Doctor> showDoctorByIncome(Double income) throws Exception;
	
	
}
