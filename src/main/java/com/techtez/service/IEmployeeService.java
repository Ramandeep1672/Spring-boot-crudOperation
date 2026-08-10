package com.techtez.service;

import java.util.List;

import com.techtez.entity.Employee;

public interface IEmployeeService {
	
	public String registerEmployee(Employee employee);
	Employee getEmployeeById(int id);
	List<Employee> getAllEmployees();
	String updateEmployeeSalary(Integer id,Double salary );
	String updateEmployeeById(Integer id,Employee employee);
	String deleteEmployeeById(Integer id);
	

}
