package com.auth.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.auth.base.util.TokenUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getAccessToken(HttpServletRequest req, HttpServletResponse response, String username, String password, String inputRefreshToken) {
		Integer statusCode = -1;
		String message = null;
		Map<String, Object> map = new HashMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth("client", "secret");
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
	    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
	    if(null != inputRefreshToken && !inputRefreshToken.isEmpty()) {
			requestBody.add("refresh_token", inputRefreshToken);
			requestBody.add("grant_type", "refresh_token");
	    }else {
	    	requestBody.add("username", username);
			requestBody.add("password", password);
			requestBody.add("grant_type", "password");
	    }
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
		try {
			ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://localhost:8080/sparktg-test-app/oauth/token", httpEntity, Map.class);
			statusCode = responseEntity.getStatusCodeValue();
			Map bodyMap = responseEntity.getBody();
			String accessToken = (String) bodyMap.get("access_token");
			String refreshToken = (String) bodyMap.get("refresh_token");
			tokenUtil.setCookie(req, response, "access_token", accessToken);
			tokenUtil.setCookie(req, response, "refresh_token", refreshToken);
			
		} catch (HttpStatusCodeException e) {
			statusCode = e.getStatusCode().value();
			message = "Incorrect username or password.";
			map.put("MESSAGE", message);
		}
		map.put("STATUS_CODE", statusCode);
		
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getShows(HttpServletRequest req,HttpServletResponse rsp, int showId) {
		Map responseMap = new HashMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer "+tokenUtil.getToken(req, rsp, "access_token"));
		
		HttpEntity<Map> httpEntity = new HttpEntity<>(headers);
		try {
			ResponseEntity<Map> responseEntity = restTemplate.exchange("http://localhost:8080/resource-service/show-info?show-id="+showId, HttpMethod.GET, httpEntity, Map.class);
			responseMap = responseEntity.getBody();
			
		} catch (HttpStatusCodeException e) {
			int statusCode = e.getStatusCode().value();
			if(statusCode==401) {
				getAccessToken(req, rsp, null, null, tokenUtil.getToken(req, rsp, "refresh_token"));
				responseMap = getShows(req, rsp, showId);
			}else {
				e.printStackTrace();
			}
		}
		
		return responseMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> getFilteredShows(HttpServletRequest req,HttpServletResponse resp, String premierDate) {
		List<Map> response = new ArrayList<>();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer "+tokenUtil.getToken(req, resp, "access_token"));
		
		HttpEntity<List> httpEntity = new HttpEntity<>(headers);
		try {
			ResponseEntity<List> responseEntity = restTemplate.exchange("http://localhost:8080/resource-service/filter-shows?premier-date="+premierDate, HttpMethod.GET, httpEntity, List.class);
			response = responseEntity.getBody();
			
		} catch (HttpStatusCodeException e) {
			int statusCode = e.getStatusCode().value();
			if(statusCode==401) {
				getAccessToken(req, resp, null, null, tokenUtil.getToken(req, resp, "refresh_token"));
				response = getFilteredShows(req, resp, premierDate);
			}
			e.printStackTrace();
		}
		
		return response;
	}

}
