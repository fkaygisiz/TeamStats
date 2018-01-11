package com.fatih.stats.service;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fatih.stats.model.Chart;
import com.fatih.stats.validation.Measures;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartServiceTest {

	@Autowired
	private ChartService chartService;

	@SuppressWarnings("unchecked")
	@Test
	public void shouldGetCorrectNumbersForCorrectTeams() {

		Chart chart = chartService.getChart("team", Arrays.asList(Measures.values()));
		assertTrue(chart.getCategories().size() == 5);
		assertTrue(chart.getSeries().size() == 3);
		int realMadridIndex = chart.getCategories().indexOf("Real Madrid");
		int barcelonaIndex = chart.getCategories().indexOf("Barcelona");
		List<Integer> championsLeagueCupCountList = chart.getSeries().stream()
				.filter(e -> e.getName().equals(Measures.CHAMPIONS.getValue())).findFirst().get().getData();
		List<Integer> leaguesCountList = chart.getSeries().stream()
				.filter(e -> e.getName().equals(Measures.LEAGUES.getValue())).findFirst().get().getData();
		List<Integer> revenueList = chart.getSeries().stream()
				.filter(e -> e.getName().equals(Measures.REVENUE.getValue())).findFirst().get().getData();

		assertTrue(12 == championsLeagueCupCountList.get(realMadridIndex).intValue());
		assertTrue(5 == championsLeagueCupCountList.get(barcelonaIndex).intValue());

		assertTrue(33 == leaguesCountList.get(realMadridIndex).intValue());
		assertTrue(24 == leaguesCountList.get(barcelonaIndex).intValue());

		assertTrue(625 == revenueList.get(realMadridIndex).intValue());
		assertTrue(620 == revenueList.get(barcelonaIndex).intValue());
		System.out.println(chart);
	}

}
