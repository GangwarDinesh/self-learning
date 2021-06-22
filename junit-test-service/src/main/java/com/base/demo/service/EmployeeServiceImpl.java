package com.base.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.demo.dao.EmployeeDao;
import com.base.demo.entity.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public Employee getEmp(int empId) {
		return employeeDao.getOne(empId);
	}

}
