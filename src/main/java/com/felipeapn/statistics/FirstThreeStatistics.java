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
import com.felipeapn.model.StatisticsType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirstThreeStatistics implements StatisticsCalculatorStrategy {

	@Override
	public List<StatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, Map<Timestamp, Candle> mapCandle) {
		
		log.info("Starting calculate First Three Algorithim from {} to {}", start, end);
		
		LocalDateTime iteratorDate = start;
		//TODO: Refact for a default method on interface
		while (iteratorDate.getMinute() % 5 != 0) {
			log.info("Starting calculate First Three Algorithim from {} to {}", iteratorDate, end);
			iteratorDate = iteratorDate.plusMinutes(1);
		}
		
		StatisticsDto statisticsDto = new StatisticsDto();
		List<StatisticsDto> statisticsDtos = new ArrayList<>();
		int pastDirectionCount = 0;		
		while (iteratorDate.isBefore(end)) {
			
			statisticsDto = new StatisticsDto();
			statisticsDto.setStatisticsType(StatisticsType.FIRST_THREE);
			statisticsDto.setTime(Timestamp.valueOf(iteratorDate));
			
			
			log.info("Start Calculating quadrant {} ", iteratorDate);
			
			log.info("Check direction on previews quadrant");
			
			pastDirectionCount += directionToSum(mapCandle.get(Timestamp.valueOf(iteratorDate.minusMinutes(5))));
			pastDirectionCount += directionToSum(mapCandle.get(Timestamp.valueOf(iteratorDate.minusMinutes(4))));
			pastDirectionCount += directionToSum(mapCandle.get(Timestamp.valueOf(iteratorDate.minusMinutes(3))));
			
			if (pastDirectionCount == 0) {
				statisticsDto.setResult(ResultEnum.NOT_EVALUATED);
			} else {
				statisticsDto.setResult(ResultEnum.LOST);
				if (pastDirectionCount > 0) {
					for (int i=2; i <= 4; i++)
						if (mapCandle.get(Timestamp.valueOf(iteratorDate.plusMinutes(i))).getDirection() == CandleDirectionEnum.UP) {
							statisticsDto.setResult(ResultEnum.WIN);
							statisticsDto.setTryToWin(i - 1);
							break;
						}
				} else {
					for (int i=2; i <= 4; i++)
						if (mapCandle.get(Timestamp.valueOf(iteratorDate.plusMinutes(i))).getDirection() == CandleDirectionEnum.DOWN) {
							statisticsDto.setResult(ResultEnum.WIN);
							statisticsDto.setTryToWin(i - 1);
							break;
						}
				}
			}
			
			statisticsDto.setPastDirectionCount(pastDirectionCount);
			
			statisticsDtos.add(statisticsDto);
			
			pastDirectionCount = 0;
			iteratorDate = iteratorDate.plusMinutes(5);
			
		}
		
		return statisticsDtos;
	}
	
	private int directionToSum(Candle candle) {
		
		if (candle == null)
			return 0;
		
		if (candle.getDirection() == CandleDirectionEnum.UP)
			return 1;
		
		if (candle.getDirection() == CandleDirectionEnum.DOWN)
			return -1;
			
		return 0;
	}
		
}
