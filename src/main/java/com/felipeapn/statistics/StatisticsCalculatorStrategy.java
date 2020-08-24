package com.felipeapn.statistics;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.felipeapn.model.Candle;
import com.felipeapn.model.CandleDirectionEnum;
import com.felipeapn.model.StatisticsDto;

public interface StatisticsCalculatorStrategy {
	
	public List<StatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, Map<Timestamp, Candle> mapCandle);

	default LocalDateTime getIteratorDate (LocalDateTime iteratorDate) {
		
		while (iteratorDate.getMinute() % 5 != 0) {
			iteratorDate = iteratorDate.plusMinutes(1);
		}
		
		return iteratorDate;
	}
	
	default int directionToSum(Candle candle) {
		
		if (candle == null)
			return 0;
		
		if (candle.getDirection() == CandleDirectionEnum.UP)
			return 1;
		
		if (candle.getDirection() == CandleDirectionEnum.DOWN)
			return -1;
			
		return 0;
	}
}
