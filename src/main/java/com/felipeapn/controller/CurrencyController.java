package com.felipeapn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipeapn.model.CurrencyDto;
import com.felipeapn.service.CurrencyService;

@RestController
@RequestMapping(path = "currency")
public class CurrencyController {

	@Autowired
	private CurrencyService currencyService;

	@GetMapping(path = "currencies")
	public List<CurrencyDto> getCurrencies() throws Exception {
		
		return currencyService.getCurrencies();
	}
}
