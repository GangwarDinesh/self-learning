package com.base.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ComponentScan(basePackages = "com.base.demo")
class EmployeeControllerTest {

	@Autowired
	private MockMvc mvc; 
	
	@Test
	void getEmpAPI() throws Exception {
	    mvc.perform( MockMvcRequestBuilders
	    	      .get("/api/employee?id=1")
	    	      .accept(MediaType.APPLICATION_JSON))
	    	      .andDo(MockMvcResultHandlers.print())
	    	      .andExpect(status().isOk())
	    	      //.andReturn().getResponse().getContentAsString();
	    	      .andExpect(MockMvcResultMatchers.jsonPath("$.empId").exists())
	    	      .andExpect(MockMvcResultMatchers.jsonPath("$.empId").isNotEmpty());
	}

}
