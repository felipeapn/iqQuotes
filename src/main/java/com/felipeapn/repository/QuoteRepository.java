package com.felipeapn.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.felipeapn.model.Quote;
import com.felipeapn.model.QuoteId;


public interface QuoteRepository extends JpaRepository<Quote, QuoteId> {
	
	@Query("select q from Quote q where q.timeQuote between :start and :end and currencyId = :currencyId")
	List<Quote> findAllWithTimeBetweenAndCurrencyId (
			@Param("start") Timestamp start,
			@Param("end") Timestamp end,
			@Param("currencyId") int currencyId );
	
	//default method
	default Map<Timestamp, Quote> findAllWithTimeBetweenAndCurrencyIdToMap (
			Timestamp start, Timestamp end, int currencyId) {
		return findAllWithTimeBetweenAndCurrencyId(start, end, currencyId)
				.stream().collect(Collectors.toMap(Quote::getTimeQuote, q -> q));
	}

}
