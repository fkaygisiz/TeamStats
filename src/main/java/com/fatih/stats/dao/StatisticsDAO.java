package com.fatih.stats.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

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

	public void writeStats() {
		System.out.println(
				"queryCountInSeconds: " + queryCountInSeconds.values().stream().mapToInt(AtomicInteger::get).sum());
		System.out.println(
				"queryCountInMinutes: " + queryCountInMinutes.values().stream().mapToInt(AtomicInteger::get).sum());
		System.out.println(
				"requestCountInSeconds: " + requestCountInSeconds.values().stream().mapToInt(AtomicInteger::get).sum());
		System.out.println(
				"requestCountInMinutes: " + requestCountInMinutes.values().stream().mapToInt(AtomicInteger::get).sum());
		System.out.println("queryCountInSeconds :");
		queryCountInSeconds.keySet().stream().forEach(System.out::println);

		System.out.println("queryCountInMinutes :");
		queryCountInMinutes.keySet().stream().forEach(System.out::println);
	}

	public void increaseRequestCountInSeconds(String formattedTimeInSecond) {
		increaseCountsByTime(requestCountInSeconds, formattedTimeInSecond);
	}

	public void increaseRequestCountInMinutes(String formattedTimeInSecond) {
		increaseCountsByTime(requestCountInMinutes, formattedTimeInSecond);
	}

}
