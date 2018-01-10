package com.fatih.stats.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fatih.stats.dao.StatisticsDAO;
import com.fatih.stats.model.Statistics;
import com.fatih.stats.validation.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTest {

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private StatisticsDAO statisticsDAO;

	@Before
	public void setUp() throws Exception {
		
		//thenReturn(new AtomicInteger(20));
	}

	@Test
	public void testToMockUpPrivateMethod() {
		AtomicInteger queryCount = statisticsDAO.getQueryCount(TimeUnit.MINUTES, "abc");
		System.out.println("adsd " + queryCount.get());
		Statistics statistics = statisticsService.getStatistics(20, TimeUnit.MINUTES, 20);
		System.out.println(statistics);
	}
}
