package com.demo.app.exceptions;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.demo.app.controller")
public class ControllerAdviceExcpHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({AccessDeniedException.class, ArithmeticException.class})
	public ResponseEntity<Object> handleAccessDeniedException(Exception e, WebRequest req){
		return new ResponseEntity<>("Access Denied!", new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
	
	@ModelAttribute
	public void setGlobalValue(Model model) {
		model.addAttribute("msg", "Hi! This is global value.");
	}
}
