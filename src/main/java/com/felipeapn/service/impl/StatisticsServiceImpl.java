package com.felipeapn.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeapn.model.Candle;
import com.felipeapn.model.StatisticsDto;
import com.felipeapn.service.CandleService;
import com.felipeapn.service.StatisticsService;
import com.felipeapn.statistics.StatisticsCalculatorStrategy;
import com.felipeapn.statistics.StatisticsCalculatorStrategyContext;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {
		
	@Autowired
	private CandleService candleService;

	@Override
	public List<StatisticsDto> getStatistics(LocalDateTime from, LocalDateTime to, int currencyId,
			StatisticsCalculatorStrategy statisticsCalculatorStrategy) {
		log.info("Call calculator");
		StatisticsCalculatorStrategyContext calculator = new StatisticsCalculatorStrategyContext(statisticsCalculatorStrategy);
		
		log.info("Ask candles Map to candle service - from {} to {}", from, to);
		Map<Timestamp, Candle> mapCandle = candleService.getMapCandle(from, to, currencyId);
				
		return calculator.getStatistics(from, to, mapCandle);
	}

}
