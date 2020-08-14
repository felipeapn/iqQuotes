package com.felipeapn.statistics;

public class StatisticsCalculatorStrategyContext {

	private StatisticCalculatorStrategy calculator;
	
	public StatisticsCalculatorStrategyContext(StatisticCalculatorStrategy calculator) {
		super();
		this.calculator = calculator;
	}

	public String getStatistics(String nome) {
		
		return this.calculator.getStatistics(nome);
	}
}
