package com.felipeapn.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
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
@Table(name = "candle")
@IdClass(CandleId.class)
public class Candle implements Serializable {
	
	@Id
	private Timestamp timeCandle;
	
	@Id
	private int currencyId;
	
	@NotNull
	private BigDecimal openedValue;

	@NotNull
	private BigDecimal closedValue;
	
	@NotNull
	@Column(name = "candle_size")
	private int candleMinuteSize;
	
	private CandleDirectionEnum direction;

}
