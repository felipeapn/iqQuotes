package com.felipeapn.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "quote")
@IdClass(QuoteId.class)
public class Quote implements Serializable {

	@Id
	private Timestamp timeQuote;
	
	@Id
	private int currencyId;
	
	@NotNull
	private BigDecimal value;
	
	@NotNull
	private int	volume;

}
