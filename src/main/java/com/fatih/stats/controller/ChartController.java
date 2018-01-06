package com.fatih.stats.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatih.stats.model.ChartInput;

@RestController
public class ChartController {

	@RequestMapping(name = "/chart")
	public ChartInput getChart(@Validated @RequestBody ChartInput chartInput) {
		System.out.println(chartInput);
		System.out.println("Hello");
		ChartInput ci = new ChartInput();
		String[] dimension = { "team" };
		ci.setDimensions(dimension);
		String[] measures = { "champions", "league" };
		ci.setMeasures(measures);
		return ci;
	}
}
