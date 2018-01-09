package com.fatih.stats.service;

import static com.fatih.stats.util.StatUtils.convertStringToMinuteFormat;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fatih.stats.dao.StatisticsDAO;

@Service
public class AsyncService {
	
	@Autowired
	private StatisticsDAO statisticsDAO;
	
	
	@Async("processExecutor")
	public void increseRequestCount(String formattedTimeInSecond) {
		statisticsDAO.increaseRequestCountInSeconds(formattedTimeInSecond);
		statisticsDAO.increaseRequestCountInMinutes(convertStringToMinuteFormat(formattedTimeInSecond));
	}

	@Async("processExecutor")
	public void increaseQueryCount(String formattedTimeInSecond) {
		statisticsDAO.increaseQueryCountInSeconds(formattedTimeInSecond);
		statisticsDAO.increaseQueryCountInMinutes(convertStringToMinuteFormat(formattedTimeInSecond));
	}
	
	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println(currentTimeMillis);
		System.out.println(currentTimeMillis/1000);
		System.out.println(currentTimeMillis/60000);
		System.out.println(currentTimeMillis/60000*60000);
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(currentTimeMillis);
		System.out.println(instance);
		instance.setTimeInMillis(currentTimeMillis/60000*60000);
		System.out.println(instance);
		//LocalDate parsedDate = LocalDate.parse(text, formatter);
	}
}
