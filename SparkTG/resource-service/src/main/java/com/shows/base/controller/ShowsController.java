package com.shows.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shows.base.service.ShowsService;

@RestController
public class ShowsController {
Logger logger = LoggerFactory.getLogger(ShowsController.class);
	
	@Autowired
	private ShowsService showsService;
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/show-info")
	@PreAuthorize("hasRole('ADMIN')")
	public Map getInfo(HttpServletRequest request, @RequestParam("show-id") int showId){
		logger.info("## Show Id ## {} ",showId);
		return showsService.getShows(showId);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/filter-shows")
	public List<Map> getFilteredInfo(HttpServletRequest request, @RequestParam("premier-date") String premierDate){
		logger.info("## Premier Date ## {} ",premierDate);
		
		return showsService.getFilteredShows(premierDate);
	}
}
