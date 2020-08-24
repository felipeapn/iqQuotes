package com.felipeapn.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipeapn.model.CurrencyDto;
import com.felipeapn.service.CurrencyService;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
    ObjectMapper objectMapper;
	
	@MockBean
	CurrencyService currencyService;

	@Test
	void testGetCurrencies() throws Exception {
		
		System.out.println(buildCurrencies());
		
		given(currencyService.getCurrencies()).willReturn(buildCurrencies());
		
		mockMvc.perform(get("/currency/currencies")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("currency/currencies", 
						responseFields(
								fieldWithPath("[]").description("List of currencies"),
								fieldWithPath("[].id").description("Currendi ID - Must be used in candle and statistic"),
								fieldWithPath("[].name").description("Currency Name"),
								fieldWithPath("[].ticker").description("Currency Ticker")
								)
							)
						);
	}
	
	List<CurrencyDto> buildCurrencies() {
		
		List<CurrencyDto> currencyDtos = null;
		
		try {
			currencyDtos = objectMapper.readValue(new File("src/test/resources/currencies.json"), new TypeReference<List<CurrencyDto>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return currencyDtos;
	}

}
