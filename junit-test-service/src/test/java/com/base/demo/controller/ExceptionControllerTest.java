package com.base.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.base.demo.exceptions.BadArgumentsException;
import com.base.demo.exceptions.InternalException;
import com.base.demo.exceptions.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class ExceptionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testResourceNotFoundException() throws Exception {
		String exceptionParam = "not_found";
		mockMvc.perform(get("/api/exceptions/excep/{exception_id}", exceptionParam)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
		.andExpect(result -> assertEquals("resource not found", result.getResolvedException().getMessage()));
	}
	
	@Test
	void testBadRequestException() throws Exception {
		String exceptionParam = "bad_arguments";
		
		mockMvc.perform(get("/api/exceptions/excep/{exception_id}", exceptionParam)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
		.andExpect(result-> assertEquals("bad arguments", result.getResolvedException().getMessage()));
	}
	
	@Test
	void testInternalServerErrorException() throws Exception{
		String exceptionParam = "dummy";
		
		mockMvc.perform(get("/api/exceptions/excep/{exception_id}", exceptionParam)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof InternalException))
				.andExpect(result -> assertEquals("internal error", result.getResolvedException().getMessage()));
		
	}

}
