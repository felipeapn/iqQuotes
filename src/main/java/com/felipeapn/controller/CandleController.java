package com.felipeapn.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Candle>> getCandle(
			@RequestParam String from, @RequestParam String to, @RequestParam String currencyId) {
		
		List<Candle> candles = candleService.getMapCandle( LocalDateTime.parse(from), LocalDateTime.parse(to), Integer.parseInt(currencyId))
					.values().stream().collect(Collectors.toList());
		
		return new ResponseEntity<>(candles, HttpStatus.OK);
		
	}
	
}
