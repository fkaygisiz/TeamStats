package com.fatih.stats.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fatih.stats.validation.EnumValidator;
import com.fatih.stats.validation.TimeUnit;

public class StatisticsInput {

	@Min(value = 1)
	private int last;

	@EnumValidator(enumClazz = TimeUnit.class, message = "timeunit.values.error")
	@NotNull(message = "timeunit.null.error")
	private String timeUnit;

	@Min(value = 1)
	private int mavgPoints;

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public int getMavgPoints() {
		return mavgPoints;
	}

	public void setMavgPoints(int mavgPoints) {
		this.mavgPoints = mavgPoints;
	}

}
