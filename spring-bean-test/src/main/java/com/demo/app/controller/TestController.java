package com.demo.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/details")
	public ResponseEntity<Map<String, String>> getDetails(Model model){
		Map<String, String> map = new HashMap<>();
		map.put("name", "Dinesh");
		map.put("globalMsg", model.getAttribute("msg").toString());
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(map, headers, HttpStatus.OK);
		
	}
}
