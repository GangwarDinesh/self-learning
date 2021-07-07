package com.base.app.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/file")
public class FileController {
	
	private Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Value("${spring.servlet.multipart.location}")
	private String fileBasePath;

	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path path = Paths.get(fileBasePath+fileName);
		logger.info("Complete path {}",path);
		try{
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e) {
			logger.error("Exception occurred- {}",e.getMessage());
		}
		String downloadFilePath = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/api/file/download/")
			.path(fileName)
			.toString();
		return ResponseEntity.ok(downloadFilePath);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/download/{fileName}")
	public ResponseEntity download(@PathVariable("fileName") String fileName) {
		Path path = Paths.get(fileBasePath+fileName);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			logger.error("Exception occurred-# {}",e.getMessage());
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/jpg"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+resource.getFilename()+"\"").body(resource);
	}
}
