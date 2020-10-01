package com.felipeapn.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import com.felipeapn.model.RangeSummarizedDto;
import com.felipeapn.model.StatisticsDto;
import com.felipeapn.model.StatisticsSummarizeDto;
import com.felipeapn.statistics.FirstThreeStatistics;
import com.felipeapn.statistics.StatisticsCalculatorStrategy;

public interface StatisticsService {

	List<StatisticsDto> getStatistics(LocalDateTime parse, LocalDateTime parse2, int currencyId, StatisticsCalculatorStrategy statisticsCalculatorStrategy);

	StatisticsSummarizeDto getStatisticsSummarized(LocalDateTime from, LocalDateTime to, int currencyId,
			StatisticsCalculatorStrategy statisticsCalculatorStrategy);

	List<StatisticsSummarizeDto> getRangSummarized(RangeSummarizedDto rangeSummarizedDto);

}
