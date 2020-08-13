package com.felipeapn.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.felipeapn.model.QuoteList;
import com.felipeapn.service.IqQuotesService;

@RestController
@RequestMapping(path = "iq")
public class IqQuotesController {

	@Autowired
	private IqQuotesService iqQuotesService;

	@GetMapping(path = "quotes")
	public ResponseEntity<String> getQuotes () {
		
		iqQuotesService.getQuotes();
		
		return null;
	}
}
