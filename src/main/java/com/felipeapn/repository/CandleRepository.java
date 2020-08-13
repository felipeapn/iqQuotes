package com.felipeapn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felipeapn.model.Candle;
import com.felipeapn.model.CandleId;

public interface CandleRepository extends JpaRepository<Candle, CandleId>{

}
