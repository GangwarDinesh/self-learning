package com.resource.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/details")
	@PreAuthorize("hasAuthority('create_profile')")
	public String getDetails() {
		return "User details found";
	}
}
