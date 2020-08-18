package com.felipeapn.service;

import java.util.List;

import com.felipeapn.model.CurrencyDto;

public interface CurrencyService {

	List<CurrencyDto> getCurrencies() throws Exception;

}
