package com.fatih.stats.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Chart {

	private Set<String> categories = new HashSet<>();
	
	private List<NameDataPair> series = new ArrayList<>();

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public List<NameDataPair> getSeries() {
		return series;
	}

	public void setSeries(List<NameDataPair> series) {
		this.series = series;
	}
	
}
