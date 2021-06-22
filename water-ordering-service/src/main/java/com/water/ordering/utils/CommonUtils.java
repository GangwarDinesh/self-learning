package com.water.ordering.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.water.ordering.entity.Order;

public class CommonUtils {

	Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	public Date convertStringToDate(String dateTime) {
		Date date = new Date();
		try {
			date = Date.from(LocalDateTime.parse(dateTime).atZone(ZoneId.systemDefault()).toInstant());
		} catch (Exception e) {
			logger.error("Exception in convertStringToDate {}", e.getMessage());
		}
		return date;
	}
	
	public String generateOrderId(String farmId) {
		Random r;
		try {
			r = SecureRandom.getInstanceStrong();
			int low = 1;
			int high = 1000;
			int result = r.nextInt(high-low) + low;
			return "ORD-"+farmId+result;
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}  
		
		return "ORD-";
	}
	
	public String convertDateToString(Date date) {
		String dateStr = null; 
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			dateStr = df.format(date);
		} catch (Exception e) {
			logger.error("Exception in convertDateToString {}", e.getMessage());
		}
		return dateStr;
	}
	
	public int getHoursFromDateTime(Date currentDateTime, Date orderDateTime) {
		final int MILLI_TO_HOUR = 1000 * 60;
	    return (int) (currentDateTime.getTime()-orderDateTime.getTime()) / MILLI_TO_HOUR;
	}
	public boolean compareDuration(Date currentTimeStamp, Order order) {
		int calculatedDuration = getHoursFromDateTime(currentTimeStamp, order.getStartDateTime());
		return calculatedDuration > order.getDuration();
	}
}
