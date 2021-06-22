package com.water.ordering.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws ParseException {
		
		String dateStr = null; 
		try {
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			dateStr = df1.format(new Date());
			System.out.println("############"+df1.parse(dateStr+".000000"));
			
			 final int MILLI_TO_HOUR = 1000 * 60 * 60;
			    int hour =  (int) (df1.parse("2021-06-18 12:12:10.000000").getTime()-df1.parse(dateStr).getTime()) / MILLI_TO_HOUR;
			    System.out.println(hour);
		} catch (Exception e) {
			String error = String.format("Exception in convertDateToString ", e.getMessage());
		}
	}

}
