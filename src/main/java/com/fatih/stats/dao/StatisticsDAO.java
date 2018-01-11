package com.fatih.stats.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.fatih.stats.validation.TimeUnit;

@Repository
public class StatisticsDAO {

	private Map<String, AtomicInteger> queryCountInSeconds = new ConcurrentHashMap<>();
	private Map<String, AtomicInteger> queryCountInMinutes = new ConcurrentHashMap<>();
	private Map<String, AtomicInteger> requestCountInSeconds = new ConcurrentHashMap<>();
	private Map<String, AtomicInteger> requestCountInMinutes = new ConcurrentHashMap<>();

	public void increaseQueryCountInSeconds(String formattedTimeInSecond) {
		increaseCountsByTime(queryCountInSeconds, formattedTimeInSecond);
	}

	public void increaseQueryCountInMinutes(String formattedTimeInSecond) {
		increaseCountsByTime(queryCountInMinutes, formattedTimeInSecond);
	}

	private void increaseCountsByTime(Map<String, AtomicInteger> timeCountMap, String formattedTimeInSecond) {
		AtomicInteger countBySeconds = timeCountMap.get(formattedTimeInSecond);
		if (countBySeconds != null) {
			countBySeconds.incrementAndGet();
		} else {
			timeCountMap.put(formattedTimeInSecond, new AtomicInteger(1));
		}
	}

	public void increaseRequestCountInSeconds(String formattedTimeInSecond) {
		increaseCountsByTime(requestCountInSeconds, formattedTimeInSecond);
	}

	public void increaseRequestCountInMinutes(String formattedTimeInSecond) {
		increaseCountsByTime(requestCountInMinutes, formattedTimeInSecond);
	}

	public AtomicInteger getRequestCount(TimeUnit timeUnit, String formattedDateTime) {
		return getCountFromMap((timeUnit == TimeUnit.MINUTES) ? requestCountInMinutes : requestCountInSeconds,
				formattedDateTime);
	}

	private AtomicInteger getCountFromMap(Map<String, AtomicInteger> map, String formattedDateTime) {
		return map.getOrDefault(formattedDateTime, new AtomicInteger());
	}

	public AtomicInteger getQueryCount(TimeUnit timeUnit, String formattedDateTime) {
		return getCountFromMap((timeUnit == TimeUnit.MINUTES) ? queryCountInMinutes : queryCountInSeconds,
				formattedDateTime);
	}

}
