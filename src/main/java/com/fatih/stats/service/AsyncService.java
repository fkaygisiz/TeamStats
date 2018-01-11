package com.fatih.stats.service;

import static com.fatih.stats.util.StatUtils.convertStringToMinuteFormat;

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

}
