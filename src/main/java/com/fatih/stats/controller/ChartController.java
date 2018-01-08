package com.fatih.stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatih.stats.model.Chart;
import com.fatih.stats.model.ChartInput;
import com.fatih.stats.service.ChartService;

@RestController
public class ChartController {
	
	@Autowired
	private ChartService chartService;

	@RequestMapping(name = "/chart")
	public ResponseEntity<Chart> getChart(@Validated @RequestBody ChartInput chartInput) {
		Chart chart = chartService.getChart(chartInput.getDimensions()[0],chartInput.getMeasures());
		return ResponseEntity.ok(chart);
	}
}
