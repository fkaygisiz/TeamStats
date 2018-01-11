package com.fatih.stats;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fatih.stats.controller.ChartController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamStatsApplicationTests {

	@Autowired
	private ChartController chartController;

	@Test
	public void contextLoads() {
		assertNotNull(chartController);
	}

}
