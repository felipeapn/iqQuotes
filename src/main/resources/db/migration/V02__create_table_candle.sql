CREATE TABLE candle (
	time_candle TIMESTAMP NOT NULL,
	currency_id BIGINT(20) NOT NULL,
	opened_value DECIMAL(10,6) NOT NULL,
	closed_value DECIMAL(10,6) NOT NULL,
	candle_size BIGINT (20) NOT NULL,
	direction CHARACTER (1),
	PRIMARY KEY (time_candle, currency_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;