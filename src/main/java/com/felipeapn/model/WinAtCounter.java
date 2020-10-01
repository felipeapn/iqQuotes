package com.felipeapn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WinAtCounter {
	
	private Integer winAt;
	private Integer winAtTimes;

}
