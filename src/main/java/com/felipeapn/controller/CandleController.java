package com.felipeapn.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.felipeapn.model.Candle;
import com.felipeapn.service.CandleService;

@RestController
@RequestMapping(path = "candle")
public class CandleController {

	@Autowired
	private CandleService candleService;

	@GetMapping
	public Map<Timestamp, Candle> getCandle(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		
		return candleService.getMapCandle(
				LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId));
		
	}
	
}
