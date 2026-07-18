package com.tcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tcs.entity.Doctor;
import com.tcs.repository.DoctorRepo;

@Service
public class DoctorService implements IDoctorService{
	
	@Autowired
	private DoctorRepo repo;

	@Override
	public Iterable<Doctor> showAllDoctor(Boolean asc, String... props) throws Exception {
		//prepare sort object
		Sort sort=Sort.by(asc?Sort.Direction.ASC:Sort.Direction.DESC,props);
		Iterable<Doctor> doctors=repo.findAll(sort);
		return doctors;
	}

	@Override
	public Page<Doctor> showAllDoctorByPageNo(int pageNo, int pageSize) throws Exception {
		//prepare pageble object
		Pageable pageble=PageRequest.of(pageNo, pageSize);
		//call find all method passing pageble object
		Page<Doctor> page=repo.findAll(pageble);
		// return page object
		return page;
	}

	@Override
	public Page<Doctor> showAllDoctorByPageAndSorting(int pageNo, int pageSize, Boolean asc, String... prop)
			throws Exception {
		// prepapre sort object
		Sort sort=Sort.by(asc?Sort.Direction.ASC:Sort.Direction.DESC,prop);
		
		// create pageble object
		Pageable pagable=PageRequest.of(pageNo, pageSize,sort);
		
		//call findAll method pasing pageble object
		
		Page<Doctor> page=repo.findAll(pagable);
		
		//return page object
		return page;
	}

	@Override
	public void showAllDoctorPegination(int pageSize) throws Exception {
		
		// get the count of record
		Long count=repo.count();
		// get pages count
		Long pagesCount=count/pageSize;
		
		if(count%pageSize!=0) {
			pagesCount++;
		}
		
		for(int i=0;i<pagesCount;i++) {
			// get pageble object or each page
			Pageable pageable=PageRequest.of(i, pageSize);
			// get doctor list of each page pagable object
			Page<Doctor> page=repo.findAll(pageable);
			 //display each pgae records
		      System.out.println("====Page no::"+(page.getNumber()+1)+"/"+page.getTotalPages()+"=====records are");
		      page.forEach(System.out::println);
		      System.out.println("----------------------");
		}
		
	}

}
