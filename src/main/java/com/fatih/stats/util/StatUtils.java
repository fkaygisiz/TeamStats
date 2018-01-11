package com.fatih.stats.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fatih.stats.validation.TimeUnit;

public class StatUtils {

	public static String getFormattedDateTime(LocalDateTime date, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return date.format(formatter);
	}

	public static String convertStringToMinuteFormat(String formattedTimeInSecond) {
		return formattedTimeInSecond.substring(0, TimeUnit.MINUTES.getFormat().length());
	}
}
