package com.felipeapn.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.felipeapn.model.Candle;
import com.felipeapn.model.CandleId;

public interface CandleRepository extends JpaRepository<Candle, CandleId> {

	@Query("select c from Candle c where c.timeCandle between :start and :end and c.currencyId = :currencyId")
	List<Candle> findWithTimeBetweenAndCurrencyId (
			@Param("start") Timestamp start,
			@Param("end") Timestamp end,
			@Param("currencyId") int currencyId );
	
	//default method
	default Map<Timestamp, Candle> findWithTimeBetweenAndCurrencyIdToMap(Timestamp start, Timestamp end, int currencyId) {
		return findWithTimeBetweenAndCurrencyId(start, end, currencyId).stream().collect(Collectors.toMap(Candle::getTimeCandle, c -> c));
		
	}
}
