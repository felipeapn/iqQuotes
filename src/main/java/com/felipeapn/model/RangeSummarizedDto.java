package com.felipeapn.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RangeSummarizedDto implements Serializable {
	
	private Integer currency;
	private List<StatisticsTypeEnum> statisticType;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private LocalDateTime timeFrom;
	private LocalDateTime timeTo;
	
}
