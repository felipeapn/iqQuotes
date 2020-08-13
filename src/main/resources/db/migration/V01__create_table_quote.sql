CREATE TABLE quote (
	time_quote TIMESTAMP NOT NULL,
	currency_id BIGINT(20) NOT NULL,
	value DECIMAL(10,6) NOT NULL,
	volume DECIMAL(10,6) NOT NULL,
	PRIMARY KEY (time_quote, currency_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;