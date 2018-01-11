package com.fatih.stats.service;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fatih.stats.dao.StatisticsDAO;
import com.fatih.stats.model.Statistics;
import com.fatih.stats.util.StatUtils;
import com.fatih.stats.validation.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTest {

	@Autowired
	private StatisticsDAO statisticsDAO;

	@Autowired
	private StatisticsService statisticsService;

	public void increaseStatistics(int epochIncrease, int statisticCount) {
		LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(epochIncrease, 0, ZoneOffset.UTC);
		for (int i = 0; i < statisticCount; i++) {// To have an enough data for minutes and seconds
			String formattedDateTimeInMin = StatUtils.getFormattedDateTime(dateFromBase, TimeUnit.MINUTES.getFormat());
			String formattedDateTimeInSec = StatUtils.getFormattedDateTime(dateFromBase, TimeUnit.SECONDS.getFormat());
			for (int j = 0; j < i; j++) {
				statisticsDAO.increaseQueryCountInSeconds(formattedDateTimeInSec);
				statisticsDAO.increaseQueryCountInMinutes(formattedDateTimeInMin);
				statisticsDAO.increaseQueryCountInSeconds(formattedDateTimeInSec);
				statisticsDAO.increaseQueryCountInMinutes(formattedDateTimeInMin);

				statisticsDAO.increaseRequestCountInMinutes(formattedDateTimeInMin);
				statisticsDAO.increaseRequestCountInSeconds(formattedDateTimeInSec);
			}
			dateFromBase = dateFromBase.plusSeconds(1);
		}

	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldGetCorrectStatisticsForMinutes() {
		increaseStatistics(600, 400);// 1970-01-01 00:10:00
		LocalDateTime ofEpochSecond = LocalDateTime.ofEpochSecond(999, 0, ZoneOffset.UTC);// 1970-01-01 00:16:39
		Statistics statistics = statisticsService.getStatistics(5, TimeUnit.MINUTES, 3, ofEpochSecond);

		List<String> categories = statistics.getChart().getCategories();
		String[] expectedCategories = { "1970-01-01 00:12", "1970-01-01 00:13", "1970-01-01 00:14", "1970-01-01 00:15",
				"1970-01-01 00:16" };
		assertTrue(categories.containsAll(Arrays.asList(expectedCategories)));
		int[] expectedQueriesData = { 8970 * 2, 12570 * 2, 16170 * 2, 19770 * 2, 15180 * 2 };
		assertTrue(statistics.getTotalQueries() == Arrays.stream(expectedQueriesData).sum());

		int[] expectedRequestsData = { 8970, 12570, 16170, 19770, 15180 };
		assertTrue(statistics.getTotalRequests() == Arrays.stream(expectedRequestsData).sum());

		double[] mavgExpectedData = { 6570.0, 10170.0, 13770.0, 17370.0, 16875.0 };
		List<Double> mavgData = statistics.getChart().getSeries().stream().filter(e -> e.getName().equals("mavg"))
				.findFirst().get().getData();
		for (int i = 0; i < expectedRequestsData.length; i++) {
			assertTrue(Double.compare(mavgData.get(i), mavgExpectedData[i]) == 0);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldGetCorrectStatisticsForSeconds() {
		increaseStatistics(1200, 400);// 1970-01-01 00:20:00
		LocalDateTime ofEpochSecond = LocalDateTime.ofEpochSecond(1599, 0, ZoneOffset.UTC);// 1970-01-01 00:26:39
		Statistics statistics = statisticsService.getStatistics(5, TimeUnit.SECONDS, 3, ofEpochSecond);

		List<String> categories = statistics.getChart().getCategories();
		String[] expectedCategories = { "1970-01-01 00:26:35", "1970-01-01 00:26:36", "1970-01-01 00:26:37",
				"1970-01-01 00:26:38", "1970-01-01 00:26:39" };
		assertTrue(categories.containsAll(Arrays.asList(expectedCategories)));
		int[] expectedQueriesData = { 395 * 2, 396 * 2, 397 * 2, 398 * 2, 399 * 2 };
		assertTrue(statistics.getTotalQueries() == Arrays.stream(expectedQueriesData).sum());

		int[] expectedRequestsData = { 395, 396, 397, 398, 399 };
		assertTrue(statistics.getTotalRequests() == Arrays.stream(expectedRequestsData).sum());

		double[] mavgExpectedData = { 394.33, 395.33, 396.33, 397.33, 398.33 };
		List<Double> mavgData = statistics.getChart().getSeries().stream().filter(e -> e.getName().equals("mavg"))
				.findFirst().get().getData();
		for (int i = 0; i < expectedRequestsData.length; i++) {
			assertTrue(Double.compare(mavgData.get(i), mavgExpectedData[i]) == 0);
		}
	}
}
