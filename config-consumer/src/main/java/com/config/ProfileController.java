package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.config.model.MemberProfileConfiguration;

@RestController
public class ProfileController {

	@Autowired
	private MemberProfileConfiguration product;
	
	@GetMapping("/profile")
	public MemberProfileConfiguration getConfig() {
		return product;
	}
}
