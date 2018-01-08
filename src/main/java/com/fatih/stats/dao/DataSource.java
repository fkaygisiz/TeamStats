package com.fatih.stats.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class DataSource {

	private static final String MILAN = "Milan";
	private static final String LIVERPOOL = "Liverpool";
	private static final String BAYERN_MUNICH = "Bayern Munich";
	private static final String BARCELONA = "Barcelona";
	private static final String REAL_MADRID = "Real Madrid";

	private Map<String, Integer> revenueMap = new HashMap<>();
	private Map<String, Integer> championsMap = new HashMap<>();
	private Map<String, Integer> leaguesMap = new HashMap<>();

	// private Map<String,>

	@PostConstruct
	public void init() {
		fillRevenueMap();
		fillChampionsMap();
		fillLeaguesMap();
	}

	private void fillLeaguesMap() {
		leaguesMap.put(REAL_MADRID, 33);
		leaguesMap.put(BARCELONA, 24);
		leaguesMap.put(BAYERN_MUNICH, 26);
		leaguesMap.put(LIVERPOOL, 18);
		leaguesMap.put(MILAN, 18);
	}

	private void fillChampionsMap() {
		championsMap.put(REAL_MADRID, 12);
		championsMap.put(BARCELONA, 5);
		championsMap.put(BAYERN_MUNICH, 5);
		championsMap.put(LIVERPOOL, 5);
		championsMap.put(MILAN, 7);
	}

	private void fillRevenueMap() {
		revenueMap.put(REAL_MADRID, 625);
		revenueMap.put(BARCELONA, 620);
		revenueMap.put(BAYERN_MUNICH, 600);
		revenueMap.put(LIVERPOOL, 400);
		revenueMap.put(MILAN, 250);
	}

	public Map<String, Integer> getData(String dataName) {
		switch (dataName) {
		case "revenue":
			return revenueMap;
		case "champions":
			return championsMap;
		case "leagues":
			return leaguesMap;
		default:
			return new HashMap<>();
		}
	}

}
