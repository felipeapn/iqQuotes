package com.felipeapn.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsSummarizeDto {
	
	private StatisticsTypeEnum statisticsType;
	private Timestamp from;
	private Timestamp to;
	private Integer currencyId;
	private Integer assessmentCount;
	private Integer winCount;
	private Integer lossCount;
	private Integer notEvaluated;
	private List<WinAtCounter> winsAt;
	private List<WinAtCounter> winsAt1Streak;

	public void setWinsAt (Map<Integer, Integer> winAtMap) {
		this.winsAt = this.winAtMapToList(winAtMap);
	}
	
	public void setWinsAt1Streak (Map<Integer, Integer> winAtMap) {
		this.winsAt1Streak = this.winAtMapToList(winAtMap);
	}

	private List<WinAtCounter> winAtMapToList(Map<Integer, Integer> winAtMap) {

		return winAtMap
				.entrySet()
				.stream()
				.map(e -> {
					WinAtCounter winAtDto = new WinAtCounter();
					winAtDto.setWinAt(e.getKey());
					winAtDto.setWinAtTimes(e.getValue());
					return winAtDto;
				}).collect(Collectors.toList());
	}
}
