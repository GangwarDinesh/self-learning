package com.base.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MicroService2Controller {
	
	private Logger log = LoggerFactory.getLogger(MicroService2Controller.class);

	@GetMapping("/hello/{name}")
	public String sayHello(@PathVariable("name") String name) {
		log.info("Input value in microservice2 > {}",name);
		return "Hello "+name;
	}
}
