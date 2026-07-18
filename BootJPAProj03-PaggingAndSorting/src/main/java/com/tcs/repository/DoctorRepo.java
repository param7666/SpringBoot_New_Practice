package com.tcs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tcs.entity.Doctor;

public interface DoctorRepo extends CrudRepository<Doctor, Long>, PagingAndSortingRepository<Doctor, Long>{

}
