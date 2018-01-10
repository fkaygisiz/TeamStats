package com.fatih.stats.model;

import java.util.ArrayList;
import java.util.List;

public class NameDataPair<T> {

	private String name;
	private List<T> data = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
