package com.demo.app.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class EmployeeBean implements InitializingBean, DisposableBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Bean initialized....");

	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Bean destroyed....");
		
	}
	
	public void testMethod() {
		System.out.println("Test method invoked...");
	}

}
