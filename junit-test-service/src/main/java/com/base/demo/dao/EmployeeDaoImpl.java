package com.base.demo.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.demo.entity.Employee;
import com.base.demo.repository.EmployeeRepository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Employee getOne(int empId) {
		Optional<Employee> opt = employeeRepository.findById(empId);
		if(opt.isPresent()) {
			return opt.get();
		}else {
			return null;
		}
	}

	@Override
	public void save(Employee emp) {
		employeeRepository.save(emp);
		
	}

}
