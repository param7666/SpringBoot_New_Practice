package com.param.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.param.entity.Department;

public interface DepartmentRepo extends  JpaRepository<Department,Integer> {

}
