package com.auth.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.auth.base.service.LoginService;

@Controller
public class LoginController {

Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;
	
	@GetMapping("/")
	public String login(Model model) {
		return "login";
	}
	
	@PostMapping("/logoutSuccess")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession(false).invalidate();
		Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/sparktg-test-app");
            response.addCookie(cookie);
        }
		return "redirect:/";
	}
	
	@PostMapping("/error")
	public String error() {
		return "login";
	}
	
	@GetMapping("/failed")
	public String index(Model model) {
		return "redirect:/";
	}
	

	@PostMapping("/loginProcessing")
	public String loginProcessing(RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password) {
		Map<String, Object> responseMap = loginService.getAccessToken(request, response, username, password, null);
		if((Integer)responseMap.get("STATUS_CODE")==200) {
			return "home";
		}else {
			redirectAttributes.addFlashAttribute("error", "Incorrect username or password.");
			return "redirect:/";
		}
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/show-info")
	@Validated
	public ResponseEntity<Map<String, ?>> getInfo(RedirectAttributes redirectAttributes,HttpServletRequest req, HttpServletResponse rsp, @RequestParam("show-id") int showId){
		logger.info("## Show Id ## {} ",showId);
		Map response = loginService.getShows(req,rsp, showId);
		Map<String, Map> responseMap = new HashMap<>();
		responseMap.put("response", response);
		return new ResponseEntity<>(responseMap, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/filter-shows")
	@Validated
	public ResponseEntity<Map<String, ?>> getFilteredInfo(RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse rsp, @RequestParam("premier-date") String premierDate){
		logger.info("## Premier Date ## {} ",premierDate);
		List<Map> response = loginService.getFilteredShows(request, rsp, premierDate);
		Map<String, List<Map>> responseMap = new HashMap<>();
		responseMap.put("response", response);
		return new ResponseEntity<>(responseMap, new HttpHeaders(), HttpStatus.OK);
	}
}
