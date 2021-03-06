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
	
		log.info("getCandle - Date and time from {} to {} and currencyID -> {}", from, to, currencyId);
		
		LocalDateTime count = from;
		
		Map<Timestamp, Quote> mapQuote = quoteService.getMapQuote(from, to.plusMinutes(1), currencyId);
		
		log.info("Map of quotes {} ", mapQuote);
		Candle candle = new Candle();
		
		while (count.isBefore(to)) {

			Quote quote = mapQuote.get(Timestamp.valueOf(count.withSecond(0)));
			
			if (quote == null)
				break;
			
			candle.setTimeCandle(quote.getTimeQuote());
			candle.setCurrencyId(currencyId);
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
		
		log.info("CandleService getMapCandle from {}, to {} currencyId -> {}", from, to, currencyId);
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
