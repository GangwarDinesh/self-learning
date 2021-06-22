package com.user.app.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailsController {

	@GetMapping("/details")
	public Map<String, String> getUserDetails(){
		System.out.println("request came on "+LocalDateTime.now() + "  ++++++++++++++++++++++");
		Map<String, String> response = new HashMap<>();
		response.put("name", "Dinesh Gangwar");
		response.put("email", "dineshgngwr@gmail.com");
		response.put("mobile", "9699288328");
		response.put("age", "29");
		response.put("country", "India");
		return response;
	}
}
