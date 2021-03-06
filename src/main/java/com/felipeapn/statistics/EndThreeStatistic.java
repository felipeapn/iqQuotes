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
public class EndThreeStatistic implements StatisticsCalculatorStrategy {

	@Override
	public List<StatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, Map<Timestamp, Candle> mapCandle) {
		
		log.info("Starting calculate End Three Algorithim from {} to {}", start, end);
		log.info("Map candle -> {}", mapCandle);
		
		LocalDateTime iteratorDate= getIteratorDate(start);
		log.info("Adjust time from {} to {}", iteratorDate, end);
		
		log.info("Map of candles {}", mapCandle);
		StatisticsDto statisticsDto = new StatisticsDto();
		List<StatisticsDto> statisticsDtos = new ArrayList<>();
		int pastDirectionCount = 0;		
		while (iteratorDate.isBefore(end)) {
			
			statisticsDto = new StatisticsDto();
			statisticsDto.setStatisticsType(StatisticsTypeEnum.END_THREE);
			statisticsDto.setTime(Timestamp.valueOf(iteratorDate));
			statisticsDto.setCurrencyID(mapCandle.get(Timestamp.valueOf(start)).getCurrencyId());
					
			pastDirectionCount += directionToSum(mapCandle.get(Timestamp.valueOf(iteratorDate.minusMinutes(1))));
			pastDirectionCount += directionToSum(mapCandle.get(Timestamp.valueOf(iteratorDate.minusMinutes(2))));
			pastDirectionCount += directionToSum(mapCandle.get(Timestamp.valueOf(iteratorDate.minusMinutes(3))));
			
			if (pastDirectionCount == 0) {
				statisticsDto.setResult(ResultEnum.NOT_EVALUATED);
			} else {
				statisticsDto.setResult(ResultEnum.LOST);
				if (pastDirectionCount > 0) {
					for (int i=0; i <= 2; i++) {
						if (mapCandle.get(Timestamp.valueOf(iteratorDate.plusMinutes(i))).getDirection() == CandleDirectionEnum.DOWN) {
							//log.info("values of evaluating UP candle {}", mapCandle.get(Timestamp.valueOf(iteratorDate.plusMinutes(i))));
							statisticsDto.setResult(ResultEnum.WIN);
							statisticsDto.setTryToWin(i + 1);
							break;
						}
					}
						
				} else {
					for (int i=0; i <= 2; i++) {
						if (mapCandle.get(Timestamp.valueOf(iteratorDate.plusMinutes(i))).getDirection() == CandleDirectionEnum.UP) {
							//log.info("values of evaluating DOWN candle {}", mapCandle.get(Timestamp.valueOf(iteratorDate.plusMinutes(i))));
							statisticsDto.setResult(ResultEnum.WIN);
							statisticsDto.setTryToWin(i + 1);
							break;
						}
						
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


}
