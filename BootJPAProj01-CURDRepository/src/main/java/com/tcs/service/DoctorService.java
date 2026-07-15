package com.tcs.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.entity.Doctor;
import com.tcs.exception.DoctorNotFoundException;
import com.tcs.repository.DoctorRepo;

@Service
public class DoctorService implements IDoctorService{
	
	@Autowired
	private DoctorRepo repo;

	@Override
	public String inserDoctor(Doctor d) throws Exception {
		System.out.println("DoctorService.inserDoctor() "+d.getId());
		Long id=repo.save(d).getId();
		System.out.println("After save called..."+d.getId());
		return "Doctor object saved with id "+id;
	}

	@Override
	public Optional<Doctor> getDoctorById(Long id) throws Exception {
		System.out.println("DoctorService.getDoctorById() "+id);
		Optional<Doctor> d=repo.findById(id);
		if(d.isEmpty()) throw new DoctorNotFoundException("Doctor not Found with "+id);
		else return d;
	}

	@Override
	public Iterable<Doctor> showAllDoctor() throws Exception {
		System.out.println("DoctorService.showAllDoctor()");
		return repo.findAll();
	}

	@Override
	public String RegisterAllDoctor(Iterable<Doctor> allDoc) throws Exception {
		Iterable<Doctor> dids=repo.saveAll(allDoc);
		StringBuilder sb=new StringBuilder("Doctor saved with ids ");
		
		for(Doctor doc:dids) {
			sb.append(doc.getId()).append(", ");
		}
		return sb.toString();
	}

	@Override
	public String checkDoctorExit(Long id) throws Exception {
		if(repo.existsById(id)) return "Doctor Exist with id"+id;
		else return "Doctor does not Exist with id"+id;
	}

	@Override
	public Long getAllDoctorCount() throws Exception {
		return repo.count();
	}

	@Override
	public String deleteDoctorById(Long id) throws Exception {
		System.out.println("DoctorService.deleteDoctorById()");
		if(repo.existsById(id)) {
		repo.deleteById(id);
		return "Doctor deleted with id = "+id;
		}else {
			return "Doctor does not Exist with id"+id;
		}
	}
	
	

}
