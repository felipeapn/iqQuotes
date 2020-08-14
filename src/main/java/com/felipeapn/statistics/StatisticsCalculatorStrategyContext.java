package com.felipeapn.statistics;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.felipeapn.model.Candle;
import com.felipeapn.model.StatisticsDto;

public class StatisticsCalculatorStrategyContext {

	private StatisticsCalculatorStrategy calculator;
	
	public StatisticsCalculatorStrategyContext(StatisticsCalculatorStrategy calculator) {
		super();
		this.calculator = calculator;
	}

	public List<StatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, Map<Timestamp, Candle> mapCandle) {
		
		return this.calculator.getStatistics(start, end, mapCandle);
	}
}
