package com.techtez.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techtez.entity.Employee;
import com.techtez.repo.IEmployeeRepository;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	
	private static final Logger logger=LogManager.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private IEmployeeRepository repo;

	@Override
	public String registerEmployee(Employee employee) { 
		logger.debug("Register Employee : {}",employee);
		Employee emp=repo.save(employee);
		logger.info("Employee Saved with id : {}",emp.getId());
		
		return "Employee is save With Emp id : " +emp.getId() ;
	}

	@Override
	public Employee getEmployeeById(int id) {
		logger.debug("Searching Employee in database with id: {}",id);
		Employee employee=repo.findById(id).orElseThrow(()->
		{
				logger.error("Employee Not Found With id : {}",id);
				return new IllegalArgumentException("Invalid Id");
				});
		logger.debug("Employee found.");
		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		logger.info("Received Request to Get All Employees.");
		List<Employee> employee=repo.findAll();
		logger.info("Employee Fetched Successfully Having size: {} :",employee.size());
		return employee;
	}

	@Override
	public String updateEmployeeSalary(Integer id,Double salary) {
		logger.debug("Request to update Employee Salary Having id: {}",id);
		
		Employee emp=repo.findById(id).orElseThrow(()->
		{
			logger.error("Employee Not Found With id : {}",id);
		return new IllegalArgumentException("Employee Not found");
		});
		//take Employee object and update data
		emp.setSalary(salary);
		repo.save(emp);
		logger.info("Employee salary Updated Successfully having id: {}",id);
		return  "Employee Salary is Updated with emp Id : "+id;
	}

	@Override
	public String updateEmployeeById(Integer id, Employee employee) {
		logger.debug("Request to update Employee Detail Having id : {}",id);
		Optional<Employee> opt=repo.findById(id);
		if(opt.isPresent())
		{
			Employee emp=opt.get();
			BeanUtils.copyProperties(employee, emp);
			repo.saveAndFlush(emp);
		}else
		{
			logger.error("Employee Not Found With id : {}",id);
			throw new IllegalArgumentException("Invalid id");
		}
		logger.info("Employee Details Updated Successfully having id :{}",id);
		return "Employee is updated having id: "+id;
	}

	@Override
	public String deleteEmployeeById(Integer id) {
		logger.debug("Request to delete Employee Having id : {}",id);
		repo.findById(id).orElseThrow(()->
		{
			logger.error("Employee Not Found With id : {}",id);
		return new RuntimeException("Employee not found with id :"+id);
		});
		
		repo.deleteById(id);
		logger.info("Employee Deleted Successfully having id :{}",id);
		return "Employee is Deleted having id : "+id;
	}

}
