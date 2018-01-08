package com.fatih.stats.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatUtils {

	private StatUtils() {
	    throw new IllegalStateException("Utility class");
	  }
	
	public static final String SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
	
	public static String getCurrentDateTimeFormat(String format) {
		LocalDateTime  date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return date.format(formatter);
	}
	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SECOND_FORMAT);
		LocalDateTime parse = LocalDateTime.parse("2017-05-05 10:33:23",formatter);
		System.out.println(parse);
	}
}
