package com.felipeapn.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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
import com.felipeapn.model.StatisticsDto;
import com.felipeapn.service.StatisticsService;
import com.felipeapn.statistics.FirstThreeStatistics;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	StatisticsService statisticsService;

	@Test
	void testGetFistThreeStatistic() throws Exception {
		
		given(statisticsService.getStatistics(
				any(LocalDateTime.class), any(LocalDateTime.class), anyInt(), any(FirstThreeStatistics.class)))
			.willReturn(buildFisrtThree());
		
		mockMvc.perform(get("/statistic/firstThree")
				.param("from", "2020-08-17T11:00:00")
				.param("to", "2020-08-17T11:10:00")
				.param("currencyId", "1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("statistic/firstThree", 
					requestParameters(
							parameterWithName("from").description("Date from to query candle. Format 2020-08-17T09:08:00"),
							parameterWithName("to").description("Date to to query candle. Format 2020-08-17T09:08:00"),
							parameterWithName("currencyId").description("Id of the pair wants to get. All pair on /currency/currencies")
							),
					responseFields(
							fieldWithPath("[]").description("List of results by 5 minutes"),
							fieldWithPath("[].statisticsType").description("Type of statistics"),
							fieldWithPath("[].time").description("Time evaluated"),
							fieldWithPath("[].result").description("If it was a Win, a Lost or Not Evaluated"),
							fieldWithPath("[].tryToWin").description("In which of the 3 trys it wins"),
							fieldWithPath("[].pastDirectionCount").description("Which direction was calculated on past")
							)));

	}

	@Test
	void testGetEndThreeStatistic() {
		//fail("Not yet implemented");
	}

	@Test
	void testGetMidThreeStatistic() {
		//fail("Not yet implemented");
	}

	List<StatisticsDto> buildFisrtThree() {

		List<StatisticsDto> StatisticsDtos = null;

		try {
			StatisticsDtos = objectMapper.readValue(new File("src/test/resources/firstthrees.json"),
					new TypeReference<List<StatisticsDto>>() {
					});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return StatisticsDtos;
	}
}
