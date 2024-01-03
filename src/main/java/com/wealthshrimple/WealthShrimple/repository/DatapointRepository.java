package com.wealthshrimple.WealthShrimple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wealthshrimple.WealthShrimple.entity.StockRecord;

public interface DatapointRepository extends JpaRepository <StockRecord, Long> {
	
}
