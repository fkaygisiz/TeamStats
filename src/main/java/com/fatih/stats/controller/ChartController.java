package com.fatih.stats.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fatih.stats.model.Chart;
import com.fatih.stats.model.ChartInput;
import com.fatih.stats.service.ChartService;
import com.fatih.stats.validation.Measures;

@RestController
@RequestMapping("/chart")
public class ChartController {

	@Autowired
	private ChartService chartService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Chart> getChart(@Validated @RequestBody ChartInput chartInput) {
		List<Measures> measures = Arrays.asList(chartInput.getMeasures()).stream().map(e->Measures.valueOf(e.toUpperCase())).collect(Collectors.toList());
		Chart chart = chartService.getChart(chartInput.getDimensions()[0], measures);
		return ResponseEntity.ok(chart);
	}

}
