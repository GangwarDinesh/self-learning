package com.base.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.demo.exceptions.BadArgumentsException;
import com.base.demo.exceptions.InternalException;
import com.base.demo.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/api/exceptions")
public class ExceptionController {
	
	@GetMapping("/excep/{exception_id}")
	public void getSpecificException(@PathVariable("exception_id") String pException) {
	    if("not_found".equals(pException)) {
	        throw new ResourceNotFoundException("resource not found");
	    }
	    else if("bad_arguments".equals(pException)) {
	        throw new BadArgumentsException("bad arguments");
	    }
	    else {
	        throw new InternalException("internal error");
	    }
	}
}
