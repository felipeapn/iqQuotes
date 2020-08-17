package com.felipeapn.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeapn.model.Candle;
import com.felipeapn.model.CandleDirectionEnum;
import com.felipeapn.model.Quote;
import com.felipeapn.repository.CandleRepository;
import com.felipeapn.repository.QuoteRepository;
import com.felipeapn.service.CandleService;
import com.felipeapn.service.IqQuotesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CandleServiceImpl implements CandleService {

	@Autowired
	private IqQuotesService quoteService;
	
	@Autowired
	private CandleRepository candleRepository;
	
	@Override
	public void getCandle(LocalDateTime from, LocalDateTime to, int candleMinuteSize, int currencyId) {
	
		//TODO: Make a test to check if the quotes needed to that time range are on database, otherwise get it from web site.
		
		log.info("Date and time from {}", from);
		
		LocalDateTime count = from;
		
		Map<Timestamp, Quote> mapQuote = quoteService.getMapQuote(from, to, 1);
		
		Candle candle = new Candle();
		
		while (count.isBefore(to)) {

			Quote quote = mapQuote.get(Timestamp.valueOf(count.withSecond(0)));
			
			candle.setTimeCandle(quote.getTimeQuote());
			candle.setCurrencyId(1); //TODO: put it on parameters
			candle.setCandleMinuteSize(candleMinuteSize);
			candle.setOpenedValue(quote.getValue());
			
			quote = mapQuote.get(Timestamp.valueOf(count.withSecond(59)));
			
			candle.setClosedValue(quote.getValue());
			
			candle.setDirection(calculateDiretcion(candle.getOpenedValue(), candle.getClosedValue()));
			
			candleRepository.save(candle);
			
			count = count.plusMinutes(candleMinuteSize);
		}
		 
	}

	@Override
	public Map<Timestamp, Candle> getMapCandle(LocalDateTime from, LocalDateTime to, int currencyId) {
		
		Map<Timestamp, Candle> mapCandle = candleRepository.findWithTimeBetweenAndCurrencyIdToMap(
				Timestamp.valueOf(from), Timestamp.valueOf(to), currencyId);
		
		long minutes = from.until(to, ChronoUnit.MINUTES);
		
		if (minutes == mapCandle.size())
			return mapCandle;
		
		this.getCandle(from, to, 1, currencyId);
		
		mapCandle = candleRepository.findWithTimeBetweenAndCurrencyIdToMap(
				Timestamp.valueOf(from), Timestamp.valueOf(to), currencyId);
		
		return mapCandle;
	}
	
	
//	TODO: CHECK WITH MY BOSS LUCAS THE BEST PLACE TO PUT THIS RULE.
	private CandleDirectionEnum calculateDiretcion(BigDecimal valueA, BigDecimal valueB) {
		
		switch (valueB.compareTo(valueA)) {
		case 1:
			return CandleDirectionEnum.UP;
		case 0:
			return CandleDirectionEnum.FLAT;
		case -1:
			return CandleDirectionEnum.DOWN;
		default:
			return CandleDirectionEnum.NOT_EVALUATED;
		}

	}

}
