package com.felipeapn.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felipeapn.model.RangeSummarizedDto;
import com.felipeapn.model.StatisticsDto;
import com.felipeapn.model.StatisticsSummarizeDto;
import com.felipeapn.model.StatisticsTypeEnum;
import com.felipeapn.service.StatisticsService;
import com.felipeapn.statistics.EndThreeStatistic;
import com.felipeapn.statistics.FirstThreeStatistics;
import com.felipeapn.statistics.MidThreeStatistic;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "statistic")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Slf4j
public class StatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;
	
	@GetMapping(path = "firstThree")
	public List<StatisticsDto> getFistThreeStatistic(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatistics(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new FirstThreeStatistics());		
	}
	
	@GetMapping(path = "endThree")
	public List<StatisticsDto> getEndThreeStatistic(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatistics(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new EndThreeStatistic());
	}
	
	@GetMapping(path = "midThree")
	public List<StatisticsDto> getMidThreeStatistic(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatistics(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new MidThreeStatistic());
	}
	
	@GetMapping(path = "firstThree/summarized")
	public StatisticsSummarizeDto getFistThreeStatisticSummarized(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatisticsSummarized(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new FirstThreeStatistics());
	}
	
	@GetMapping(path = "endThree/summarized")
	public StatisticsSummarizeDto getEndThreeStatisticSummarized(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatisticsSummarized(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new EndThreeStatistic());
	}
	
	@GetMapping(path = "midThree/summarized")
	public StatisticsSummarizeDto getMidThreeStatisticSummarized(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		return statisticsService.getStatisticsSummarized(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId), new MidThreeStatistic());
	}

	@GetMapping(path = "summarized")
	public List<StatisticsSummarizeDto> getRangeSummarized (@RequestParam String currencyId, @RequestParam StatisticsTypeEnum statisticsType[],
			@RequestParam String dateFrom, @RequestParam String dateTo, 
			@RequestParam String timeFrom, @RequestParam String timeTo) {
		
		log.info("ARRIVE -> {}, {}, {}, {}, {}", dateFrom, dateTo, timeFrom, timeTo, statisticsType);
		
		System.out.println(LocalDate.parse(dateFrom.subSequence(0, 10)));
		
		RangeSummarizedDto rangeSummarizedDto = new RangeSummarizedDto();
		rangeSummarizedDto.setCurrency(Integer.parseInt(currencyId));
		rangeSummarizedDto.setStatisticType(Arrays.asList(statisticsType));
		rangeSummarizedDto.setDateFrom(LocalDate.parse(dateFrom.subSequence(0, 10)));
		rangeSummarizedDto.setDateTo(LocalDate.parse(dateTo.subSequence(0, 10)));
		rangeSummarizedDto.setTimeFrom(LocalDateTime.parse(timeFrom.subSequence(0, 16)));
		rangeSummarizedDto.setTimeTo(LocalDateTime.parse(timeTo.subSequence(0, 16)));
		
		System.out.println(rangeSummarizedDto);
		
		return statisticsService.getRangSummarized(rangeSummarizedDto);
	}
}
