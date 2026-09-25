package com.param.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

@Entity 
@Table (name = "department")
@Getter 
@Setter 
public class Department {

    @Id 
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int departmentId;

    private String departmentName;

    @OneToMany (mappedBy = "department", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    List<Employee> employees=new ArrayList<>();


    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee e) {
        employees.remove(e);
        e.setDepartment(null);
    }

}
