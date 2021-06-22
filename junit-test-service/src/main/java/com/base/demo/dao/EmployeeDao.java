package com.base.demo.dao;

import com.base.demo.entity.Employee;

public interface EmployeeDao {
	public Employee getOne(int empId);
	
	public void save(Employee emp);
}
