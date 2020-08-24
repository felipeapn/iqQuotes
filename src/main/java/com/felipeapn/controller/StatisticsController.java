package com.felipeapn.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felipeapn.model.StatisticsDto;
import com.felipeapn.service.StatisticsService;
import com.felipeapn.statistics.EndThreeStatistic;
import com.felipeapn.statistics.FirstThreeStatistics;
import com.felipeapn.statistics.MidThreeStatistic;

@RestController
@RequestMapping(path = "statistic")
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	
	@GetMapping(path = "firstThree")
	public List<StatisticsDto> getFistThreeStatistic(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatistics(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new FirstThreeStatistics());		
	}
	
	@GetMapping(path = "endThree")
	public List<StatisticsDto> getEndThreeStatistic(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatistics(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new EndThreeStatistic());
	}
	
	@GetMapping(path = "midThree")
	public List<StatisticsDto> getMidThreeStatistic(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatistics(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new MidThreeStatistic());
	}

}
