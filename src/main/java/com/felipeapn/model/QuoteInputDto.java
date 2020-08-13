package com.felipeapn.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteInputDto implements Serializable {

	private Long ts;
	private Long n;
	private BigDecimal bid;
	private BigDecimal ask;
	private BigDecimal value;
	private int	volume;
	private String phase;
	private BigDecimal raw_bid;
	private BigDecimal raw_ask;
	private boolean round;
}
