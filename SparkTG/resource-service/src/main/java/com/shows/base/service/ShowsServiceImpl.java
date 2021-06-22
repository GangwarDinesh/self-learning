package com.shows.base.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ShowsServiceImpl implements ShowsService {
	Logger logger = LoggerFactory.getLogger(ShowsServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getShows(int showId) {
		Map finalResponse = null;
		int noOfThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService service = Executors.newFixedThreadPool(noOfThreads);
		CountDownLatch latch = new CountDownLatch(2);
		
		Callable<Map> call1 = ()->{
			Map response = null;
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Map> httpEntity = new HttpEntity<>(headers);
			try {
				ResponseEntity<Map> responseEntity = restTemplate.exchange("http://api.tvmaze.com/shows/"+showId, HttpMethod.GET, httpEntity, Map.class);
				response = responseEntity.getBody();
				
			} catch (HttpStatusCodeException e) {
				logger.error("Exception in calling shows API", e.getCause());
			}
			latch.countDown();
			return response;
		};
		Callable<ConcurrentHashMap[]> call2 = ()->{
			
			ConcurrentHashMap[] response = null;
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ConcurrentHashMap[]> httpEntity = new HttpEntity<>(headers);
			try {
				ResponseEntity<ConcurrentHashMap[]> responseEntity = restTemplate.exchange("http://api.tvmaze.com/shows/"+showId+"/episodes", HttpMethod.GET, httpEntity, ConcurrentHashMap[].class);
				response = responseEntity.getBody();
				
			} catch (HttpStatusCodeException e) {
				logger.error("Exception in calling episodes API", e.getCause());
			}
			latch.countDown();
			return response;
		};
		try {
			Map response1 = service.submit(call1).get();
			ConcurrentHashMap[] response2 = service.submit(call2).get();
			List<ConcurrentHashMap> conMap = Arrays.asList(response2);
			latch.await();
			
			conMap.forEach(map->{
				map.remove("_links");
				map.remove("summary");
				map.remove("airstamp");
				map.remove("runtime");
				map.remove("airtime");
			});
			finalResponse = new HashMap<>();
			finalResponse.put("id", response1.getOrDefault("id", "0"));
			finalResponse.put("url", response1.getOrDefault("url", ""));
			finalResponse.put("name", response1.getOrDefault("name", ""));
			finalResponse.put("type", response1.getOrDefault("type", ""));
			finalResponse.put("language", response1.getOrDefault("language", ""));
			finalResponse.put("status", response1.getOrDefault("status", ""));
			finalResponse.put("runtime", response1.getOrDefault("runtime", "0"));
			finalResponse.put("premiered", response1.getOrDefault("premiered", ""));
			finalResponse.put("externals", response1.getOrDefault("externals", new HashMap<String, Object>()));
			finalResponse.put("episodes", conMap);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		
		return finalResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> getFilteredShows(String prepierDate) {
		List<Map> newList = null;
		List<Map> mapsList = null;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<List<Map>> httpEntity = new HttpEntity<>(headers);
		try {
			ResponseEntity<List> responseEntity = restTemplate.exchange("http://api.tvmaze.com/shows", HttpMethod.GET, httpEntity, List.class);
			mapsList = responseEntity.getBody();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate localDate = LocalDate.parse(prepierDate, formatter);
			mapsList = mapsList.stream().filter(d->LocalDate.parse((String)d.get("premiered"), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(localDate)).collect(Collectors.toList());
			newList = mapsList.stream().map(m->{
				Map alteredMap = new HashMap<>();
				m.forEach((k,v)->{
					if(k.equals("id") || k.equals("name")) {
						alteredMap.put(k, v);
					}
				});
				
				return alteredMap;
			}).collect(Collectors.toList());
			
		} catch (HttpStatusCodeException e) {
			logger.error("Exception in calling episodes API", e.getCause());
		}
		return newList;
	}

}
