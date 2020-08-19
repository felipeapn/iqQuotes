package com.felipeapn.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.felipeapn.model.Candle;
import com.felipeapn.service.CandleService;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(CandleController.class)
class CandleControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
    ObjectMapper objectMapper;
	
	@MockBean
	CandleService candleService;
	
	@Test
	void testGetCandle() throws Exception {
		System.out.println(buildCandles());
		given(candleService.getMapCandle(any(LocalDateTime.class), any(LocalDateTime.class), anyInt()))
			.willReturn(buildCandles());
		
		mockMvc.perform(get("/candle")
					.param("from", "2020-08-17T11:00:00")
					.param("to", "2020-08-17T11:10:00")
					.param("currencyId", "1")
					.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("candle", 
						requestParameters(
								parameterWithName("from").description("Date from to query candle. Format 2020-08-17T09:08:00"),
								parameterWithName("to").description("Date to to query candle. Format 2020-08-17T09:08:00"),
								parameterWithName("currencyId").description("Id of the pair wants to get. All pair on /currency/currencies")
								)));	
	}
	
	Map<Timestamp, Candle> buildCandles () {
		
		List<Candle> candles = null;

		try {	
			candles = objectMapper.readValue(new File("src/test/resources/candles.json"), new TypeReference<List<Candle>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return candles.stream().collect(Collectors.toMap(Candle::getTimeCandle, c -> c));
	}
	
	

}
