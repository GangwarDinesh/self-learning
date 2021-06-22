package com.service.profile.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.User;

@RestController
@RequestMapping("/services")
public class UserController {

	List<User> userList = new ArrayList<>();
	
	@GetMapping("/profiles")
	@PreAuthorize("hasAuthority('create_profile')")
	public List<User> getProfile() {
		return userList;
	}
	
	@PostMapping("/createProfile")
	@PreAuthorize("hasAuthority('create_profile')")
	public String createProfile(@RequestBody User user) {
		userList.add(user);
		return "User created successfully.";
	}
}
