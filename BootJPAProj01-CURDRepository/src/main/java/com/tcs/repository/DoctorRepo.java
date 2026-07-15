package com.tcs.repository;

import org.springframework.data.repository.CrudRepository;

import com.tcs.entity.Doctor;

public interface DoctorRepo extends CrudRepository<Doctor, Long>{

}
