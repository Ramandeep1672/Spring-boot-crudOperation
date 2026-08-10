package com.techtez.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techtez.entity.Employee;
import com.techtez.entity.SuccessResponse;
import com.techtez.service.IEmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeOperationsController {
	
	@Autowired
	private IEmployeeService service;
	
	private static final Logger logger=
			LogManager.getLogger(EmployeeOperationsController.class);
	
	
	
	@PostMapping("/save")
	public ResponseEntity<SuccessResponse<Employee>> saveEmployee(@Valid @RequestBody Employee employee,HttpServletRequest request)
	{
		logger.info("Received Request to save Employee");
		String msg=service.registerEmployee(employee);
		logger.info("Employee saved Successfully");
		SuccessResponse<Employee> response=new SuccessResponse<>();
		
		 response.setTime(LocalDateTime.now());
		    response.setStatus(HttpStatus.CREATED.value());
		    response.setMessage(msg);
		    response.setPath(request.getRequestURI());
		    response.setData(employee);
		    
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/fetch/{id}")
	public ResponseEntity<SuccessResponse<Employee>> fetchEmployeeById(@PathVariable Integer id,HttpServletRequest request)
	{
		logger.info("Fetch employee with EmpId : {}",id);
		Employee data=service.getEmployeeById(id);
		
		SuccessResponse<Employee> response=new SuccessResponse<>();
		response.setTime(LocalDateTime.now());
	    response.setStatus(HttpStatus.OK.value());
	    response.setMessage("Employee Fetched succesfully with id"+id);
	    response.setPath(request.getRequestURI());
	    response.setData(data);
	    
		logger.info("Employee fetched with id:{}",id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/fetch-all")
	public ResponseEntity<SuccessResponse<List<Employee>>> showAllEmployees(HttpServletRequest request)
	{
		logger.info("Received Request to fetch All Employees");
		List<Employee> listData=service.getAllEmployees();
		
		logger.info("All Employees fetched Successfully");
		
		SuccessResponse<List<Employee>> response=new SuccessResponse<>();
		
		response.setTime(LocalDateTime.now());
	    response.setStatus(HttpStatus.OK.value());
	    response.setMessage("All Employee Fetched succesfully");
	    response.setPath(request.getRequestURI());
	    response.setData(listData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PatchMapping("/update/{id}/{salary}")
	public ResponseEntity<SuccessResponse<Employee>> updateEmployeeSalary(@PathVariable Integer id,@PathVariable Double salary,HttpServletRequest request)
	{
		logger.info("Received request to Update Employee salary having id:{}",id);
		String msg=service.updateEmployeeSalary(id, salary);
		logger.info("Employee salary is Updated Successfully");
		
 SuccessResponse<Employee> response=new SuccessResponse<>();
		
		response.setTime(LocalDateTime.now());
	    response.setStatus(HttpStatus.OK.value());
	    response.setMessage(msg);
	    response.setPath(request.getRequestURI());
	  
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<SuccessResponse<Employee>> updateEmployeeData( @Valid @RequestBody Employee employee , @PathVariable Integer id,HttpServletRequest request)
	{
		logger.info("Received Request to Update Employee Full Data having id:{}",id);
		String msg=service.updateEmployeeById(id, employee);
		
SuccessResponse<Employee> response=new SuccessResponse<>();
		
		response.setTime(LocalDateTime.now());
	    response.setStatus(HttpStatus.OK.value());
	    response.setMessage(msg);
	    response.setPath(request.getRequestURI());
		logger.info("Employee data Is Updated Successfully");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<SuccessResponse<Employee>> deleteEmployeeData(@PathVariable Integer id,HttpServletRequest request)
	{
		logger.info("Received Request to Delete Employee with id :{}",id);
		String msg=service.deleteEmployeeById(id);
		
SuccessResponse<Employee> response=new SuccessResponse<>();
		
		response.setTime(LocalDateTime.now());
	    response.setStatus(HttpStatus.NO_CONTENT.value());
	    response.setMessage(msg);
	    response.setPath(request.getRequestURI());
		return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
	}

}
