package com.fatih.stats.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatih.stats.dao.StatisticsDAO;
import com.fatih.stats.model.Statistics;
import com.fatih.stats.util.StatUtils;
import com.fatih.stats.validation.TimeUnit;

@Service
public class StatisticsService {

	@Autowired
	private StatisticsDAO statisticsDAO;
	
	public Statistics getStatistics(int last, TimeUnit timeUnit, int mAvgPoints) {
		Statistics statistics = new Statistics(); 
		LocalDateTime  date = LocalDateTime.now();
		for(int i = 0; i< last+mAvgPoints; i++) {
			String formattedDateTime = StatUtils.getFormattedDateTime(date, timeUnit.getFormat());
			statistics.getChart().getCategories().add(formattedDateTime);
			date = date.minus(1, timeUnit.getTemporalUnit());
		}
		
		return statistics;
	}

}
