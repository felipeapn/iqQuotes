package com.felipeapn.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsDto implements Serializable {
	
	private StatisticsTypeEnum statisticsType;
	private Timestamp time;
	private ResultEnum result;
	private int tryToWin;
	private int pastDirectionCount;
	private int currencyID;

}
