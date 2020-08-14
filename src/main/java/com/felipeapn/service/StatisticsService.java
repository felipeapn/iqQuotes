package com.felipeapn.service;

import java.time.LocalDateTime;
import java.util.List;

import com.felipeapn.model.StatisticsDto;
import com.felipeapn.statistics.FirstThreeStatistics;
import com.felipeapn.statistics.StatisticsCalculatorStrategy;

public interface StatisticsService {

	List<StatisticsDto> getStatistics(LocalDateTime parse, LocalDateTime parse2, int currencyId, StatisticsCalculatorStrategy statisticsCalculatorStrategy);

}
