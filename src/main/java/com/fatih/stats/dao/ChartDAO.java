package com.fatih.stats.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChartDAO {

	@Autowired
	private DataSource dataSource;
	
	public Map<String, Integer> getNameDataPair(String dataName) {
		return dataSource.getData(dataName);
	}
	
	
}
