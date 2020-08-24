package com.felipeapn.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipeapn.model.Candle;
import com.felipeapn.model.ResultEnum;
import com.felipeapn.model.StatisticsDto;
import com.felipeapn.model.StatisticsTypeEnum;

@SpringBootTest
class EndThreeStatisticsTest {

	@Autowired
    ObjectMapper objectMapper;
	
	StatisticsCalculatorStrategy statisticsStrategy;
	
	List<StatisticsDto> endThreeResult;
	
	@Test
	void testGetStatistics() {
		
		System.out.println(buildCandles());
		
		statisticsStrategy = new EndThreeStatistic();
		
		endThreeResult = statisticsStrategy.getStatistics(
				LocalDateTime.parse("2020-08-17T08:59:00"), 
				LocalDateTime.parse("2020-08-17T09:10:00"), 
				buildCandles());
		
		System.out.println(endThreeResult);
		
		StatisticsDto statisticsDtoReturned = endThreeResult.get(1);
		
		System.out.println(statisticsDtoReturned.getTime());
		
		LocalDateTime timeExpected = LocalDateTime.parse("2020-08-17T09:05:00");
		LocalDateTime timeActual = statisticsDtoReturned.getTime().toLocalDateTime();
		
		assertTrue(timeExpected.isEqual(timeActual), "Data compareing");
		assertEquals(statisticsDtoReturned.getResult(), ResultEnum.WIN);
		assertEquals(statisticsDtoReturned.getStatisticsType(), StatisticsTypeEnum.END_THREE);
		assertEquals(statisticsDtoReturned.getTryToWin(), 1);
		assertEquals(statisticsDtoReturned.getPastDirectionCount(), 1);
		
	}

Map<Timestamp, Candle> buildCandles () {
		
		List<Candle> candles = null;

		try {	
			candles = objectMapper.readValue(new File("src/test/resources/candles.json"), new TypeReference<List<Candle>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return candles.stream().collect(Collectors.toMap(Candle::getTimeCandle, c -> c));
	}

}
