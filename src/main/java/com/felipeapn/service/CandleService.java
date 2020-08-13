package com.felipeapn.service;

import java.time.LocalDateTime;

public interface CandleService {

	void getCandle(LocalDateTime from, LocalDateTime to, int candleSize);

}
