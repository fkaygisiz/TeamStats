package com.fatih.stats.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Chart {

	private List<String> categories = new ArrayList<>();

	private List<NameDataPair> series = new ArrayList<>();

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<NameDataPair> getSeries() {
		return series;
	}

	public void setSeries(List<NameDataPair> series) {
		this.series = series;
	}

}
