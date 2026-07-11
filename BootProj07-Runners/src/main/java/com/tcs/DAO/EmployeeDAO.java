package com.tcs.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcs.model.Employee;

@Repository
public class EmployeeDAO implements IEmployeeDAO{

	@Autowired
	private DataSource ds;
	
	private static final String GET_EMP_BY_DESG="SELECT empno,ename,salary,deptno,desg from employee where desg in (?,?,?) Order by ename";
	private static final String INSERT_EMP="INSERT INTO EMPLOYEE (empno,ename,salary,deptno,desg) values (?,?,?,?,?)";
	
	@Override
	public List<Employee> getEmployeeByDesg(String desg1, String desg2, String desg3) throws Exception {
		// logic to get employee based on 
		List<Employee> list=null;
		
		try (Connection con=ds.getConnection();){
			PreparedStatement ps=con.prepareStatement(GET_EMP_BY_DESG);
			
			// set values in queryy
			ps.setString(1, desg1);
			ps.setString(2, desg2);
			ps.setString(3, desg3);
			
			ResultSet rs=ps.executeQuery();
			list=new ArrayList<Employee>();
			while(rs.next()) {
				Employee e=new Employee();
				e.setId(rs.getInt(1));
				e.setName(rs.getString(2));
				e.setSalary(rs.getDouble(3));
				e.setDeptno(rs.getInt(4));
				e.setDesg(rs.getString(5));
				list.add(e);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int insertEmployee(Employee e) throws Exception {
		int result=0;
		try(Connection con=ds.getConnection()) {
			PreparedStatement ps=con.prepareStatement(INSERT_EMP);
			ps.setInt(1, e.getId());
			ps.setString(2, e.getName());
			ps.setDouble(3,e.getSalary());
			ps.setInt(4, e.getDeptno());
			ps.setString(5, e.getDesg());
			
			result=ps.executeUpdate();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return result; 
	}

}
