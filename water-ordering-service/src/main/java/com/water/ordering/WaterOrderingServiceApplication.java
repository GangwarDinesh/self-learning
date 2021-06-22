package com.water.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.water.ordering.utils.CommonUtils;

@SpringBootApplication
@EnableScheduling
public class WaterOrderingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterOrderingServiceApplication.class, args);
	}

	@Bean
	@Scope(value = "singleton")
	public CommonUtils getCommonUtils() {
		return new CommonUtils();
	}
}
