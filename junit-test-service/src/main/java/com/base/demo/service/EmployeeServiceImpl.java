package com.base.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.demo.dao.EmployeeDao;
import com.base.demo.entity.Employee;
import com.base.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee getEmp(int empId) {
		return employeeDao.getOne(empId);
	}

	@Override
	public void printMessage(int empId) {
		
		employeeRepository.deleteById(empId);
		System.out.println("Employee with id "+empId+" has been deleted succefully.");
	}

}
