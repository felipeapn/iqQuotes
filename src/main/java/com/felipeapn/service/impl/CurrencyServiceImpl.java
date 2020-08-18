package com.felipeapn.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipeapn.model.CurrencyDto;
import com.felipeapn.service.CurrencyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {

	@Override
	public List<CurrencyDto> getCurrencies() throws Exception, JsonMappingException, IOException {
		log.info("Read json file to get currencies");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ClassLoader classLoader = getClass().getClassLoader();
		
		List<CurrencyDto> CurrencyDtos = 
				Arrays.asList(objectMapper.readValue(new File(classLoader.getResource("Currencies.json").getFile()), CurrencyDto[].class));
		
		return CurrencyDtos;
	}

}
