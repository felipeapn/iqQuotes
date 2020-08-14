package com.felipeapn.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipeapn.model.Candle;
import com.felipeapn.model.CandleDirectionEnum;
import com.felipeapn.model.Quote;
import com.felipeapn.repository.CandleRepository;
import com.felipeapn.repository.QuoteRepository;
import com.felipeapn.service.CandleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CandleServiceImpl implements CandleService {

	@Autowired
	private QuoteRepository quoteRepository;
	@Autowired
	private CandleRepository candleRepository;
	
	@Override
	public void getCandle(LocalDateTime from, LocalDateTime to, int candleMinuteSize) {
	
		//TODO: Make a test to check if the quotes needed to that time range are on database, otherwise get it from web site.
		
		LocalDateTime count = from;
		
		Timestamp start = Timestamp.valueOf(from);
		Timestamp end = Timestamp.valueOf(to);
		
		List<Quote> quotes = quoteRepository.findAllWithTimeBetweenAndCurrencyId(start, end, 1);
			
		Map<Timestamp, Quote> mapQuote = quotes.stream().collect(Collectors.toMap(Quote::getTimeQuote, q -> q));
		
		Candle candle = new Candle();
		
		while (count.isBefore(to)) {

			Quote quote = mapQuote.get(Timestamp.valueOf(count.withSecond(0)));
			
			log.info("Quote seconde 00 {}", quote);
			
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
