package com.fatih.stats.dao;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fatih.stats.util.StatUtils;
import com.fatih.stats.validation.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsDAOTest {

	@Autowired
	private StatisticsDAO statisticsDAO;

	@Test
	public void shouldIncreaseQueryCountByOneForSeconds() {
		LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(100, 0, ZoneOffset.UTC);
		TimeUnit timeUnit = TimeUnit.SECONDS;
		checkQueryCount(dateFromBase, timeUnit);
	}

	@Test
	public void shouldIncreaseQueryCountByOneForMinutes() {
		LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(100, 0, ZoneOffset.UTC);
		TimeUnit timeUnit = TimeUnit.MINUTES;
		checkQueryCount(dateFromBase, timeUnit);
	}

	@Test
	public void shouldIncreaseRequestCountByOneForMinutes() {
		LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(100, 0, ZoneOffset.UTC);
		TimeUnit timeUnit = TimeUnit.MINUTES;
		checkRequestCount(dateFromBase, timeUnit);
	}

	@Test
	public void shouldIncreaseRequestCountByOneForSeconds() {
		LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(100, 0, ZoneOffset.UTC);
		TimeUnit timeUnit = TimeUnit.SECONDS;
		checkRequestCount(dateFromBase, timeUnit);
	}

	private void checkRequestCount(LocalDateTime dateFromBase, TimeUnit timeUnit) {
		String formattedDateTime = StatUtils.getFormattedDateTime(dateFromBase, timeUnit.getFormat());
		AtomicInteger queryCount = statisticsDAO.getRequestCount(timeUnit, formattedDateTime);
		if (timeUnit == TimeUnit.SECONDS) {
			statisticsDAO.increaseRequestCountInSeconds(formattedDateTime);
		} else {
			statisticsDAO.increaseRequestCountInMinutes(formattedDateTime);
		}
		assertTrue(queryCount.get() + 1 == statisticsDAO.getRequestCount(timeUnit, formattedDateTime).get());
	}

	private void checkQueryCount(LocalDateTime dateFromBase, TimeUnit timeUnit) {
		String formattedDateTime = StatUtils.getFormattedDateTime(dateFromBase, timeUnit.getFormat());
		AtomicInteger queryCount = statisticsDAO.getQueryCount(timeUnit, formattedDateTime);
		if (timeUnit == TimeUnit.SECONDS) {
			statisticsDAO.increaseQueryCountInSeconds(formattedDateTime);
		} else {
			statisticsDAO.increaseQueryCountInMinutes(formattedDateTime);
		}
		assertTrue(queryCount.get() + 1 == statisticsDAO.getQueryCount(timeUnit, formattedDateTime).get());
	}

}
