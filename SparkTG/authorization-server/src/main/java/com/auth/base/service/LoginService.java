package com.auth.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {
	
	public Map<String, Object> getAccessToken(HttpServletRequest request, HttpServletResponse response, String username, String password, String inputRefreshToken);
	
	@SuppressWarnings("rawtypes")
	public Map getShows(HttpServletRequest response, HttpServletResponse rsp, int showId);
	
	@SuppressWarnings("rawtypes")
	public List<Map> getFilteredShows(HttpServletRequest req,HttpServletResponse resp, String premierDate);

}
