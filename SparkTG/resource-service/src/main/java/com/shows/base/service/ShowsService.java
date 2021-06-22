package com.shows.base.service;

import java.util.List;
import java.util.Map;

public interface ShowsService {

	@SuppressWarnings("rawtypes")
	public Map getShows(int showId);
	
	@SuppressWarnings("rawtypes")
	public List<Map> getFilteredShows(String prepierDate);
}
