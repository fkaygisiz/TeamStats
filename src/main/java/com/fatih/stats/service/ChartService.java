package com.fatih.stats.service;

import static com.fatih.stats.util.StatUtils.getFormattedDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatih.stats.dao.RemoteDataSource;
import com.fatih.stats.model.Chart;
import com.fatih.stats.model.NameDataPair;
import com.fatih.stats.validation.Measures;
import com.fatih.stats.validation.TimeUnit;

@Service
public class ChartService {

	@Autowired
	private RemoteDataSource remoteDataSource;

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
	public Chart getChart(String dimension, List<Measures> measures) {
		String formattedTimeInSecond = getFormattedDateTime(LocalDateTime.now(), TimeUnit.SECONDS.getFormat());
		asyncService.increseRequestCount(formattedTimeInSecond);
		List<String> categories = new ArrayList<>();
		Map<Measures, Map<String, Integer>> measureValueMap = new EnumMap<Measures, Map<String, Integer>>(
				Measures.class);
		fillCategoriesAndValuesMap(measures, categories, measureValueMap, formattedTimeInSecond);
		Chart chart = new Chart();
		chart.setCategories(categories);
		fillChartSeries(chart, measureValueMap);
		return chart;
	}

	private void fillChartSeries(Chart chart, Map<Measures, Map<String, Integer>> measureValueMap) {
		for (Map.Entry<Measures, Map<String, Integer>> entry : measureValueMap.entrySet()) {
			Map<String, Integer> dataMap = entry.getValue();
			NameDataPair<Integer> ndp = createNameDataPair(chart.getCategories(), entry.getKey(), dataMap);
			chart.getSeries().add(ndp);

		}
	}

	private void fillCategoriesAndValuesMap(List<Measures> measures, List<String> categories,
			Map<Measures, Map<String, Integer>> measureValueMap, String formattedTimeInSecond) {
		Set<String> distinctCategoryNames = new HashSet<>();
		for (Measures measure : measures) {
			Map<String, Integer> dataMap = remoteDataSource.getData(measure.getValue());
			increaseQueryCount(formattedTimeInSecond);
			measureValueMap.put(measure, dataMap);
			distinctCategoryNames.addAll(dataMap.keySet());
		}
		categories.addAll(distinctCategoryNames);
	}

	private void increaseQueryCount(String formattedTimeInSecond) {
		asyncService.increaseQueryCount(formattedTimeInSecond);
	}

	private NameDataPair<Integer> createNameDataPair(List<String> categories, Measures measurementName,
			Map<String, Integer> dataMap) {
		NameDataPair<Integer> ndp = new NameDataPair<>();
		ndp.setName(measurementName.getValue());
		for (String categorie : categories) {
			Integer value = dataMap.get(categorie);
			ndp.getData().add(value != null ? value : null);
		}
		return ndp;
	}

}
