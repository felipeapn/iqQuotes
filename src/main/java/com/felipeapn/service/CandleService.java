package com.felipeapn.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import com.felipeapn.model.Candle;

public interface CandleService {

	void getCandle(LocalDateTime from, LocalDateTime to, int candleSize, int currencyId);

	Map<Timestamp, Candle> getMapCandle(LocalDateTime from, LocalDateTime to, int currencyId);

}
