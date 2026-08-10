package com.techtez.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techtez.entity.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

}
