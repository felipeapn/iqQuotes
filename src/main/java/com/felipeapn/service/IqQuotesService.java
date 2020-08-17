package com.felipeapn.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import com.felipeapn.model.Quote;
import com.felipeapn.model.QuoteList;

public interface IqQuotesService {
	
	public QuoteList getQuotes(LocalDateTime from, LocalDateTime to, int currencyId);

	public Map<Timestamp, Quote> getMapQuote(LocalDateTime from, LocalDateTime to, int currencyId);

}
