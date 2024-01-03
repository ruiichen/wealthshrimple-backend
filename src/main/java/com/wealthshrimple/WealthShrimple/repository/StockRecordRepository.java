package com.wealthshrimple.WealthShrimple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wealthshrimple.WealthShrimple.entity.StockRecord;

public interface StockRecordRepository extends JpaRepository <StockRecord, Long> {
	StockRecord findByTicker(String ticker);
	
	List<StockRecord> findByUserName(String username);
}
