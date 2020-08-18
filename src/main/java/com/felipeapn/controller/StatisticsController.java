package com.felipeapn.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public List<StatisticsDto> getFistThreeStatistic() {
		return statisticsService.getStatistics(LocalDateTime.parse("2020-08-17T11:00:00"), LocalDateTime.parse("2020-08-17T14:59:59"), 1, new FirstThreeStatistics());		
	}
	
	@GetMapping(path = "endThree")
	public List<StatisticsDto> getEndThreeStatistic() {
		return statisticsService.getStatistics(LocalDateTime.parse("2020-08-12T12:00:00"), LocalDateTime.parse("2020-08-12T12:59:59"), 1, new EndThreeStatistic());
	}
	
	@GetMapping(path = "midThree")
	public List<StatisticsDto> getMidThreeStatistic() {
		return statisticsService.getStatistics(LocalDateTime.parse("2020-08-12T12:00:00"), LocalDateTime.parse("2020-08-12T12:59:59"), 1, new MidThreeStatistic());
	}

}
