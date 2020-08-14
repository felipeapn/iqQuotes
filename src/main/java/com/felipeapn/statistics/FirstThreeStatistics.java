package com.felipeapn.statistics;

public class FirstThreeStatistics implements StatisticCalculatorStrategy {

	@Override
	public String getStatistics(String nome) {
		
		return nome + " -> FirstThree Statistic";
	}

}
