package com.fatih.stats.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fatih.stats.validation.TimeUnit;

public class StatUtils {

	private StatUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String getFormattedDateTime(LocalDateTime date, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return date.format(formatter);
	}

	public static String convertStringToMinuteFormat(String formattedTimeInSecond) {
		return formattedTimeInSecond.substring(0, TimeUnit.MINUTES.getFormat().length());
	}
	/*
	 * public static void main(String[] args) { DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern(SECOND_FORMAT); LocalDateTime parse =
	 * LocalDateTime.parse("2017-05-05 10:33:23",formatter);
	 * System.out.println(parse); }
	 */
}
