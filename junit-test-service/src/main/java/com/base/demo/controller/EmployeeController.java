package com.base.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.base.demo.entity.Employee;
import com.base.demo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employee")
	public Employee getEmp(@RequestParam("id") int empId) {
		return employeeService.getEmp(empId);
	}
}
