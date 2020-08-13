package com.felipeapn.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.felipeapn.model.QuoteId.QuoteIdBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandleId implements Serializable {

	private Timestamp timeCandle;

	private int currencyId;

}
