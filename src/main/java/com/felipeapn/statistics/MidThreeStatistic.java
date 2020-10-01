package com.felipeapn.statistics;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.felipeapn.model.Candle;
import com.felipeapn.model.CandleDirectionEnum;
import com.felipeapn.model.ResultEnum;
import com.felipeapn.model.StatisticsDto;
import com.felipeapn.model.StatisticsTypeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MidThreeStatistic implements StatisticsCalculatorStrategy {

	@Override
	public List<StatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, Map<Timestamp, Candle> mapCandle) {
		
		log.info("Starting calculate Mid Three Algorithim from {} to {}", start, end);
		log.info("Map candle -> {}", mapCandle);
		
		LocalDateTime iteratorDate= getIteratorDate(start);
		log.info("Adjust time from {} to {}", iteratorDate, end);
		
		log.info("Map of candles {}", mapCandle);
		StatisticsDto statisticsDto = new StatisticsDto();
		List<StatisticsDto> statisticsDtos = new ArrayList<>();
	
		while (iteratorDate.isBefore(end)) {
			
			statisticsDto = new StatisticsDto();
			statisticsDto.setStatisticsType(StatisticsTypeEnum.MID_THREE);
			statisticsDto.setTime(Timestamp.valueOf(iteratorDate));
			
			int tryToWin = 1;
			for(int i = 0; i <= 5; i = i + 2) {
				
				Candle pastCandle = mapCandle.get(Timestamp.valueOf(iteratorDate.minusMinutes(5 - i)));
				Candle currentCandle = mapCandle.get(Timestamp.valueOf(iteratorDate.plusMinutes(i)));
				
				if (pastCandle != null) {
					
					if( pastCandle.getDirection() == CandleDirectionEnum.FLAT || 
							pastCandle.getDirection() == CandleDirectionEnum.NOT_EVALUATED) {
						statisticsDto.setResult(ResultEnum.NOT_EVALUATED);
						
					} else {
					
						if (pastCandle.getDirection() == CandleDirectionEnum.UP && 
								currentCandle.getDirection() == CandleDirectionEnum.DOWN) {
							statisticsDto.setResult(ResultEnum.WIN);
							statisticsDto.setTryToWin(tryToWin);
							break;
						}
						
						if (pastCandle.getDirection() == CandleDirectionEnum.DOWN && 
								currentCandle.getDirection() == CandleDirectionEnum.UP) {
							statisticsDto.setResult(ResultEnum.WIN);
							statisticsDto.setTryToWin(tryToWin);
							break;
						}
						
						statisticsDto.setResult(ResultEnum.LOST);
					} 	
				} else {
					statisticsDto.setResult(ResultEnum.NOT_EVALUATED);
				}
				
				tryToWin++;
			}
			
		statisticsDto.setPastDirectionCount(0);
			
		statisticsDtos.add(statisticsDto);
			
		iteratorDate = iteratorDate.plusMinutes(5);
			
		}
		return statisticsDtos;
	}
}
