package com.felipeapn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipeapn.statistics.EndThreeStatistic;
import com.felipeapn.statistics.FirstThreeStatistics;
import com.felipeapn.statistics.StatisticsCalculatorStrategyContext;

@RestController
@RequestMapping(path = "statistic")
public class StatisticsController {
	
	@GetMapping(path = "fistThree")
	public String getFistThreeStatistic() {
		StatisticsCalculatorStrategyContext calculator = new StatisticsCalculatorStrategyContext(new FirstThreeStatistics());
		return calculator.getStatistics("Felipe");
	}
	
	@GetMapping(path = "endThree")
	public String getEndThreeStatistic() {
		StatisticsCalculatorStrategyContext calculator = new StatisticsCalculatorStrategyContext(new EndThreeStatistic());
		return calculator.getStatistics("Felipe");
	}

}
