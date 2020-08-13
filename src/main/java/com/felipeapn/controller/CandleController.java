package com.felipeapn.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipeapn.service.CandleService;

@RestController
@RequestMapping(path = "candle")
public class CandleController {

	@Autowired
	private CandleService candleService;

	@GetMapping
	public void getCandle() {
		
		candleService.getCandle(LocalDateTime.parse("2020-08-12T12:00:00"), LocalDateTime.parse("2020-08-12T12:59:59"), 1);
		
	}
	
}
