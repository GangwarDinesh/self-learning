package com.example.circuitbreakerreading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.circuitbreakerreading.service.BookService;

@RestController
public class ReaderController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/to-read")
	@NonNull
	public String toRead() {
		return bookService.readingList();
	}
}
