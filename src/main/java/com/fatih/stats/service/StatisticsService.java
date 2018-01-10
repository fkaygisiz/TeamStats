package com.fatih.stats.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatih.stats.dao.StatisticsDAO;
import com.fatih.stats.model.NameDataPair;
import com.fatih.stats.model.Statistics;
import com.fatih.stats.util.StatUtils;
import com.fatih.stats.validation.TimeUnit;

@Service
public class StatisticsService {

	@Autowired
	private StatisticsDAO statisticsDAO;

	public Statistics getStatistics(int last, TimeUnit timeUnit, int mAvgPoints) {
		Statistics statistics = new Statistics();
		LocalDateTime date = LocalDateTime.now();

		LinkedBlockingDeque<Integer> mAvgDataHolder = new LinkedBlockingDeque<>(mAvgPoints);

		NameDataPair<Integer> requestData = new NameDataPair<>();
		requestData.setName("requests");

		NameDataPair<Integer> queriesData = new NameDataPair<>();
		queriesData.setName("queries");

		NameDataPair<Double> mAvgData = new NameDataPair<>();
		mAvgData.setName("mavg");
		for (int i = 0; i < last + mAvgPoints - 1; i++) {
			String formattedDateTime = StatUtils.getFormattedDateTime(date, timeUnit.getFormat());
			int requestCount = statisticsDAO.getRequestCount(timeUnit, formattedDateTime).get();
			if (i < last) {
				statistics.getChart().getCategories().add(formattedDateTime);
				requestData.getData().add(requestCount);
				queriesData.getData().add(statisticsDAO.getQueryCount(timeUnit, formattedDateTime).get());
			}
			mAvgDataHolder.push(requestCount);
			if (mAvgDataHolder.remainingCapacity() == 0 && i >= mAvgPoints - 1) {
				mAvgData.getData().add(calculateWeightedaverage(mAvgDataHolder));
				mAvgDataHolder.removeLast();
			}

			date = date.minus(1, timeUnit.getTemporalUnit());
		}
		Collections.reverse(statistics.getChart().getCategories());
		Collections.reverse(queriesData.getData());
		Collections.reverse(requestData.getData());
		Collections.reverse(mAvgData.getData());
		statistics.setTotalQueries(queriesData.getData().stream().mapToInt(Integer::intValue).sum());
		statistics.setTotalRequests(requestData.getData().stream().mapToInt(Integer::intValue).sum());
		statistics.getChart().getSeries().add(queriesData);
		statistics.getChart().getSeries().add(requestData);
		statistics.getChart().getSeries().add(mAvgData);

		return statistics;
	}

	private Double calculateWeightedaverage(LinkedBlockingDeque<Integer> mAvgDataHolder) {
		Iterator<Integer> iterator = mAvgDataHolder.iterator();
		int multiplier = 1;
		int totalDivider = 0;
		double total = 0;
		while (iterator.hasNext()) {
			Integer value = iterator.next();
			total += multiplier * value.doubleValue();
			totalDivider += multiplier;
			multiplier++;
		}
		return (new BigDecimal(total / totalDivider).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue();
	}

}
