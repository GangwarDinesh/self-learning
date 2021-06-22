package com.client.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.app.model.User;
import com.client.app.config.AccessToken;

@Controller
@EnableOAuth2Sso
public class HomeController extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated();
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/users")
	public ModelAndView secure() {
		ModelAndView mv = new ModelAndView();
		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", AccessToken.getAccessToken());
			HttpEntity<User> httpEntity = new HttpEntity<>(headers);
			ResponseEntity<User[]> respEntity = restTemplate.exchange("http://localhost:9292/services/profiles", HttpMethod.GET, httpEntity, User[].class);
			mv.addObject("users", respEntity.getBody());
		} catch (HttpStatusCodeException e) {
			int statusCode = e.getStatusCode().value();
			String errorMsg = e.getStatusText();
			e.printStackTrace();
			mv.addObject("ERR", true);
			mv.addObject("ERR_CODE", statusCode);
			mv.addObject("ERR_MSG", errorMsg);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		mv.setViewName("users");
		
		return mv;
	}
}
