package com.client.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

}
