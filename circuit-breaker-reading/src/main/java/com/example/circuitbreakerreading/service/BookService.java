package com.example.circuitbreakerreading.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class BookService {
	
	@Autowired
	private RestTemplate restTemplate;
		
	@HystrixCommand(
			commandKey = "readingListFromAPI",
			fallbackMethod = "reliable", 
			commandProperties = {@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "0"),
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "0"),
					@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
					},
			threadPoolKey = "hystrix.threadpool.default.keepAliveTimeMinutes")
	public String readingList() {
		URI uri = URI.create("http://localhost:8090/recommended");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restTemplate.getForObject(uri, String.class);
	}
	
	public String reliable() {
		return "Sorry! Books service is not responding. But Don't worry we are providing you some results";
	}
	

}
