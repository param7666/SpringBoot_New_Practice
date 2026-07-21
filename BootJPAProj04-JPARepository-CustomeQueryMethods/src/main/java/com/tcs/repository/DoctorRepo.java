package com.tcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.tcs.entity.Doctor;

public interface DoctorRepo extends JpaRepository<Doctor, Long>{
	
	@Query("from Doctor where specilization=:speci")
	public List<Doctor> findAllDoctorBySpecialzation(String speci);
	
	@Query("from Doctor where income>=:income")
	public List<Doctor> findDoctorByIncome(Double income);
	
	@Query("from Doctor where income between ?1 and ?2")
	public List<Object[]> findDoctorBetweenSalary(Double start, Double end) throws Exception;

}

/*

| No. | Query Type                  | Operation                                                                                     |
| --- | --------------------------- | --------------------------------------------------------------------------------------------- |
| 1   | Insert                      | Save a new doctor.                                                                            |
| 2   | Find by ID                  | Retrieve a doctor using its ID.                                                               |
| 3   | Find All                    | Display all doctors.                                                                          |
| 4   | Equality (`=`)              | Find all doctors whose specialization is **Cardiologist**.                                    |
| 5   | Comparison (`>`)            | Find doctors whose income is greater than ₹80,000.                                            |
| 6   | Range (`BETWEEN`)           | Find doctors whose income is between ₹50,000 and ₹1,00,000.                                   |
| 7   | Pattern (`LIKE`)            | Find doctors whose name starts with **"A"**.                                                  |
| 8   | Multiple Conditions (`AND`) | Find doctors whose qualification is **MBBS** and income is greater than ₹70,000.              |
| 9   | Multiple Conditions (`OR`)  | Find doctors who are either **Neurologists** or **Dermatologists**.                           |
| 10  | Sorting (`ORDER BY`)        | Display all doctors ordered by income in descending order.                                    |
| 11  | Aggregate Function          | Find the average income of all doctors.                                                       |
| 12  | Group By                    | Count the number of doctors in each specialization.                                           |
| 13  | Distinct                    | Retrieve all unique qualifications.                                                           |
| 14  | Subquery                    | Find doctors whose income is greater than the average income of all doctors.                  |
| 15  | Update (`@Modifying`)       | Increase the income of all Cardiologists by 10%.                                              |
| 16  | Delete (`@Modifying`)       | Delete doctors whose income is less than ₹30,000.                                             |
| 17  | Pagination                  | Retrieve doctors page-wise, 5 records per page.                                               |
| 18  | Projection                  | Retrieve only the doctor's **name** and **specialization** without loading the entire entity. |



*/