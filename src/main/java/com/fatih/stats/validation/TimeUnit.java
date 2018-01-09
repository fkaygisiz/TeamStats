package com.fatih.stats.validation;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;


public enum TimeUnit {
	SECONDS("seconds", "yyyy-MM-dd HH:mm:ss", ChronoUnit.SECONDS), MINUTES("minutes", "yyyy-MM-dd HH:mm", ChronoUnit.MINUTES);

	private String value;
	private String format;
	private TemporalUnit temporalUnit;
	
	
	private TimeUnit(String value, String format, TemporalUnit unit) {
		this.value = value;
		this.format = format;
		this.temporalUnit = unit;
	}

	public static TimeUnit fromValue(String val) {
		for (TimeUnit timeUnit : TimeUnit.values()) {
			if (timeUnit.getValue().equals(val)) {
				return timeUnit;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public String getFormat() {
		return format;
	}

	public TemporalUnit getTemporalUnit() {
		return temporalUnit;
	}

}
