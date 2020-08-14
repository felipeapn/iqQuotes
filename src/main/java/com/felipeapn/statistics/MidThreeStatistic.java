package com.felipeapn.statistics;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.felipeapn.model.Candle;
import com.felipeapn.model.StatisticsDto;

public class MidThreeStatistic implements StatisticsCalculatorStrategy {

	@Override
	public List<StatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, Map<Timestamp, Candle> mapCandle) {
		
		System.out.println(start);
		System.out.println(mapCandle);
		
		return null;
	}

}
