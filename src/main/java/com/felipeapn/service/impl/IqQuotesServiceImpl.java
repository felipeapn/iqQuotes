package com.felipeapn.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.felipeapn.model.Quote;
import com.felipeapn.model.QuoteInputDto;
import com.felipeapn.model.QuoteList;
import com.felipeapn.repository.QuoteRepository;
import com.felipeapn.service.IqQuotesService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class IqQuotesServiceImpl implements IqQuotesService {

	public final String PATH_ = "https://cdn.iqoption.com/api/quotes-history/quotes/3.0?to=1596706285999&from=1596704426000&active_id=1&only_round=true&_key=1597074900";
	
	public final String SCHEME = "https";
	public final String HOST = "cdn.iqoption.com";
	public final String PATH = "api/quotes-history/quotes/3.0";
	
	private final RestTemplate restTemplate;
	
	@Autowired
	private QuoteRepository quoteRepository;
	
	private BigDecimal valueBefore;
	
	public IqQuotesServiceImpl(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	@Override
	public QuoteList getQuotes() {
			
		//TODO: Parametros abaixo de dias e hora devem ser adicinado no metodos.
		LocalDate today = LocalDate.now();
		
		int startHour = 11;
		int endHour = 12;
				
		for (int i = 1; i < 6; i++ ) {
			
			LocalDateTime startDay = today.minusDays(i).atTime(startHour - 1, 54, 59);
			LocalDateTime endDay = today.minusDays(i).atTime(endHour, 59, 59);
			
			Timestamp from = Timestamp.valueOf(startDay);
			Timestamp to = Timestamp.valueOf(endDay);
			
			log.info("time to {} from {}", to.getTime(), from.getTime());
			
			ResponseEntity<QuoteList> entity = restTemplate.getForEntity(preparedUri(from, to) , QuoteList.class);
			
			persisteQuote(entity.getBody().getQuotes());
			
		}
		
		
		
        //System.out.println(restTemplate.getForEntity(PATH, String.class).getBody());
				
		//System.out.println(entity.getBody().getQuotes().size());
		
		//Timestamp stamp = new Timestamp(entity.getBody().getQuotes().get(0).getTs());

		// System.out.println(stamp.toString());
	
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
		
		//LocalDateTime localTime = stamp.toLocalDateTime();
		
		//ZonedDateTime zonedDateTime = localTime.atZone(ZoneId.of("Europe/Madrid"));
		
		//System.out.println(format.format(zonedDateTime));
		
		return null;
	}

	private void persisteQuote(List<QuoteInputDto> quotes) {

		quotes.stream()
			.filter(quote -> (new Timestamp(quote.getTs())).getSeconds() == 0 || new Timestamp(quote.getTs()).getSeconds() == 59)
			.forEach(q -> {
					Timestamp t = new Timestamp(q.getTs());
					//System.out.println(	t.getHours() + ":" + t.getMinutes() + ":" + t.getSeconds() + " -> " + q.getValue());
					Quote quote = Quote.builder()
							.timeQuote(t)
							.value(q.getValue())
							.volume(q.getVolume())
							.currencyId(1) // recuperar da  uri param activeId
							.build();

					quoteRepository.save(quote);
				});
		
	}

	private void getQuotesFromUri() {
		
	}
	
	private String preparedUri(Timestamp from, Timestamp to) {
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(SCHEME)
				.host(HOST)
				.path(PATH)
				.queryParam("to", to.getTime())
				.queryParam("from", from.getTime())
				.queryParam("active_id", "1")
				.queryParam("only_round", "true")
				.queryParam("_key", "1597074900")
				.build();
		log.info(uri.toString());
		return uri.toString();
	}
 
}
