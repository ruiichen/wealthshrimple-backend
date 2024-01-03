package com.wealthshrimple.WealthShrimple.service;

import java.util.List;

import com.wealthshrimple.WealthShrimple.dto.StockRecordData;


public interface StockRecordService {
	StockRecordData saveStockRecord(StockRecordData stockRecord);
	List<StockRecordData> getAllStockRecords();
	StockRecordData getStockRecordByTicker(final String ticker);
	List<StockRecordData> getStockRecordByUsername(String username);
}
