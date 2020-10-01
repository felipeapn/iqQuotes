package com.felipeapn.service.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeapn.model.Candle;
import com.felipeapn.model.RangeSummarizedDto;
import com.felipeapn.model.ResultEnum;
import com.felipeapn.model.StatisticsDto;
import com.felipeapn.model.StatisticsSummarizeDto;
import com.felipeapn.model.StatisticsTypeEnum;
import com.felipeapn.service.CandleService;
import com.felipeapn.service.StatisticsService;
import com.felipeapn.statistics.EndThreeStatistic;
import com.felipeapn.statistics.FirstThreeStatistics;
import com.felipeapn.statistics.MidThreeStatistic;
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
		StatisticsCalculatorStrategyContext calculator = new StatisticsCalculatorStrategyContext(
				statisticsCalculatorStrategy);

		log.info("Ask candles Map to candle service - from {} to {}", from, to);
		Map<Timestamp, Candle> mapCandle = candleService.getMapCandle(from.minusMinutes(10), to, currencyId);

		return calculator.getStatistics(from, to, mapCandle);
	}

	@Override
	public StatisticsSummarizeDto getStatisticsSummarized(LocalDateTime from, LocalDateTime to, int currencyId,
			StatisticsCalculatorStrategy statisticsCalculatorStrategy) {

		log.info("Call get Statistics");

		List<StatisticsDto> statisticsDtos = this.getStatistics(from, to, currencyId, statisticsCalculatorStrategy);

		log.info("Summarizing ...");

		StatisticsSummarizeDto summarizeDto = new StatisticsSummarizeDto();
		int winCount = 0;
		int lostCount = 0;
		int notEvaluatedCount = 0;
		int winAt1streakCount = 0;
		Map<Integer, Integer> winAt = new HashMap<Integer, Integer>();
		Map<Integer, Integer> winAt1Streak = new HashMap<Integer, Integer>();
		for (StatisticsDto statisticsDto : statisticsDtos) {
			if (statisticsDto.getResult() == ResultEnum.WIN) {
				winCount++;
				if (winAt.get(statisticsDto.getTryToWin()) == null)
					winAt.put(statisticsDto.getTryToWin(), 1);
				else
					winAt.put(statisticsDto.getTryToWin(), winAt.get(statisticsDto.getTryToWin()) + 1);

				// Calc streak of try to win 1
				if (statisticsDto.getTryToWin() == 1) {
					winAt1streakCount++;
				} else {
					if (winAt1Streak.get(winAt1streakCount) == null)
						winAt1Streak.put(winAt1streakCount, 1);
					else
						winAt1Streak.put(winAt1streakCount, winAt1Streak.get(winAt1streakCount) + 1);

					winAt1streakCount = 0;
				}

			}

			if (statisticsDto.getResult() == ResultEnum.LOST) {
				lostCount++;
			}
			if (statisticsDto.getResult() == ResultEnum.NOT_EVALUATED) {
				notEvaluatedCount++;
			}

		}

		summarizeDto.setStatisticsType(statisticsDtos.get(0).getStatisticsType());
		summarizeDto.setFrom(Timestamp.valueOf(from));
		summarizeDto.setTo(Timestamp.valueOf(to));
		summarizeDto.setCurrencyId(currencyId);
		summarizeDto.setAssessmentCount(statisticsDtos.size());
		summarizeDto.setWinCount(winCount);
		summarizeDto.setWinsAt(winAt);
		summarizeDto.setWinsAt1Streak(winAt1Streak);
		summarizeDto.setLossCount(lostCount);
		summarizeDto.setNotEvaluated(notEvaluatedCount);

		log.info("Returning data ...");
		return summarizeDto;
	}

	@Override
	public List<StatisticsSummarizeDto> getRangSummarized(RangeSummarizedDto rangeSummarizedDto) {

		List<StatisticsSummarizeDto> summarizeDtos = new ArrayList<>();
		LocalDate iteratorDate = rangeSummarizedDto.getDateFrom();

		LocalDateTime from;
		LocalDateTime to;
		int currencyId = (int) rangeSummarizedDto.getCurrency();
		System.out.println(currencyId);

		while (iteratorDate.isBefore(rangeSummarizedDto.getDateTo().plusDays(1))) {

			from = LocalDateTime.of(iteratorDate, rangeSummarizedDto.getTimeFrom().toLocalTime().withNano(0).withSecond(0));
			to = LocalDateTime.of(iteratorDate, rangeSummarizedDto.getTimeTo().toLocalTime().withNano(0).withSecond(0));
			

			for (StatisticsTypeEnum typeEnum : rangeSummarizedDto.getStatisticType()) {
				summarizeDtos
						.add(this.getStatisticsSummarized(from, to, currencyId, this.getStatisticsInstance(typeEnum)));
			}

			iteratorDate = iteratorDate.plusDays(1);
		}

		return summarizeDtos;
	}

	public StatisticsCalculatorStrategy getStatisticsInstance(StatisticsTypeEnum type) {

		switch (type) {
		case FIRST_THREE:
			return new FirstThreeStatistics();
		case END_THREE:
			return new EndThreeStatistic();
		case MID_THREE:
			return new MidThreeStatistic();
		}
		return null;
	}

}
