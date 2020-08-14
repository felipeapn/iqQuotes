package com.felipeapn.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeapn.model.Candle;
import com.felipeapn.model.StatisticsDto;
import com.felipeapn.repository.CandleRepository;
import com.felipeapn.service.StatisticsService;
import com.felipeapn.statistics.FirstThreeStatistics;
import com.felipeapn.statistics.StatisticsCalculatorStrategy;
import com.felipeapn.statistics.StatisticsCalculatorStrategyContext;

@Service
public class StatisticsServiceImpl implements StatisticsService {
	
	@Autowired
	private CandleRepository candleRepository;

	@Override
	public List<StatisticsDto> getStatistics(LocalDateTime start, LocalDateTime end, int currencyId,
			StatisticsCalculatorStrategy statisticsCalculatorStrategy) {
		
		StatisticsCalculatorStrategyContext calculator = new StatisticsCalculatorStrategyContext(statisticsCalculatorStrategy);
		
		Map<Timestamp, Candle> mapCandle = candleRepository.findWithTimeBetweenAndCurrencyIdToMap(
				Timestamp.valueOf(start), Timestamp.valueOf(end), currencyId);
		
		return calculator.getStatistics(start, end, mapCandle);
	}

}
