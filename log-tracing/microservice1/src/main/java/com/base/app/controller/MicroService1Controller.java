package com.base.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/client")
public class MicroService1Controller {
	
	private Logger log = LoggerFactory.getLogger(MicroService1Controller.class);
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/welcome/{name}")
	public String sayHello(@PathVariable("name") String name) {
		String response = restTemplate.getForObject("http://localhost:8081/microservice2/api/hello/"+name, String.class);
		log.info("Message from microservice 2 > {}",response);
		return response;
	}
}
