package com.client.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ClientController {
	
	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/userdetails")
	public ResponseEntity<Map<String, Object>> getUserDetails(){
		Map<String, Object> map = new HashMap<>();
		try {
			ResponseEntity<Map> resMap = restTemplate.getForEntity("http://users/details", Map.class);
			map = resMap.getBody();
		} catch (HttpStatusCodeException e) {
			int statusCode = e.getStatusCode().value();
			String responseBody = e.getResponseBodyAsString();
			map.put("ERR_CODE", statusCode);
			map.put("MSG_BODY", responseBody);
			
		}
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
