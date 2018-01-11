package com.fatih.stats.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fatih.stats.model.Statistics;
import com.fatih.stats.model.StatisticsInput;
import com.fatih.stats.service.StatisticsService;
import com.fatih.stats.validation.TimeUnit;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Statistics> getChart(@Validated @RequestBody StatisticsInput statisticsInput) {
		LocalDateTime date = LocalDateTime.now();
		Statistics statistics = statisticsService.getStatistics(statisticsInput.getLast(),
				TimeUnit.fromValue(statisticsInput.getTimeUnit()), statisticsInput.getMavgPoints(), date);
		return ResponseEntity.ok(statistics);
	}

}
