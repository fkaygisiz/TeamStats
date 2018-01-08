package com.fatih.stats.service;

import static com.fatih.stats.util.StatUtils.SECOND_FORMAT;
import static com.fatih.stats.util.StatUtils.getCurrentDateTimeFormat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatih.stats.dao.DataSource;
import com.fatih.stats.model.Chart;
import com.fatih.stats.model.NameDataPair;

@Service
public class ChartService {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AsyncService asyncService;

	/**
	 * 
	 * @param dimension
	 *            is not used in fact, all the time its value should be "teams" for
	 *            this assignment
	 * @param measures
	 *            names of the measurements
	 * 
	 * @see if measures more than one, then we need to think about missing points in
	 *      the series. Please have a look at
	 *      https://www.highcharts.com/demo/area-missing/gray
	 * @return
	 */
	public Chart getChart(String dimension, String[] measures) {
		String formattedTimeInSecond = getCurrentDateTimeFormat(SECOND_FORMAT);
		asyncService.increseRequestCount(formattedTimeInSecond);
		Set<String> categories = new HashSet<>();
		HashMap<String, Map<String, Integer>> measureValueMap = new HashMap<>();
		fillCategoriesAndValuesMap(measures, categories, measureValueMap, formattedTimeInSecond);
		Chart chart = new Chart();
		chart.setCategories(categories);
		fillChartSeries(chart, measureValueMap);
		return chart;
	}

	private void fillChartSeries(Chart chart, HashMap<String, Map<String, Integer>> measureValueMap) {
		for (Map.Entry<String, Map<String, Integer>> entry : measureValueMap.entrySet()) {
			Map<String, Integer> dataMap = entry.getValue();
			NameDataPair ndp = createNameDataPair(chart.getCategories(), entry.getKey(), dataMap);
			chart.getSeries().add(ndp);

		}
	}

	private void fillCategoriesAndValuesMap(String[] measures, Set<String> categories,
			HashMap<String, Map<String, Integer>> measureValueMap, String formattedTimeInSecond) {
		for (String measure : measures) {
			Map<String, Integer> dataMap = dataSource.getData(measure);
			increaseQueryCount(formattedTimeInSecond);
			measureValueMap.put(measure, dataMap);
			categories.addAll(dataMap.keySet());
		}
	}

	private void increaseQueryCount(String formattedTimeInSecond) {
		asyncService.increaseQueryCount(formattedTimeInSecond);
	}

	private NameDataPair createNameDataPair(Set<String> categories, String measurementName,
			Map<String, Integer> dataMap) {
		NameDataPair ndp = new NameDataPair();
		ndp.setName(measurementName);
		for (String categorie : categories) {
			Integer value = dataMap.get(categorie);
			ndp.getData().add(value != null ? value.doubleValue() : null);
		}
		return ndp;
	}

}
