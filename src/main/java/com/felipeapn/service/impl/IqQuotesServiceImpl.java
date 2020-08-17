package com.felipeapn.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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
	public QuoteList getQuotes(LocalDateTime from, LocalDateTime to, int currencyId) {
			
		//TODO: Parametros abaixo de dias e hora devem ser adicinado no metodos.
		//TODO: from max 7 days behind. to till 30 minutes behind.
		LocalDate today = LocalDate.now();
		
		log.info("time to {} from {}", to, from);
		
		ResponseEntity<QuoteList> entity = 
				restTemplate.getForEntity(preparedUri(from, to, currencyId) , QuoteList.class);
		
		persisteQuote(entity.getBody().getQuotes());
	
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
		
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
	
	private String preparedUri(LocalDateTime from, LocalDateTime to, int currencyId) {
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(SCHEME)
				.host(HOST)
				.path(PATH)
				.queryParam("to", Timestamp.valueOf(to).getTime())
				.queryParam("from", Timestamp.valueOf(from).getTime())
				.queryParam("active_id", "1")
				.queryParam("only_round", "true")
				.queryParam("_key", "1597074900")
				.build();
		log.info(uri.toString());
		return uri.toString();
	}

	@Override
	public Map<Timestamp, Quote> getMapQuote(LocalDateTime from, LocalDateTime to, int currencyId) {
		
		Map<Timestamp, Quote> mapQuote = quoteRepository.findAllWithTimeBetweenAndCurrencyIdToMap(
				Timestamp.valueOf(from), Timestamp.valueOf(to), 1);
		
		long minutes = from.until(to, ChronoUnit.MINUTES);
		
		if ((minutes * 2) <= mapQuote.size())
			return mapQuote;
		
		this.getQuotes(from, to, currencyId);
		
		return quoteRepository.findAllWithTimeBetweenAndCurrencyIdToMap(Timestamp.valueOf(from), Timestamp.valueOf(to), 1);
	}
 
}
