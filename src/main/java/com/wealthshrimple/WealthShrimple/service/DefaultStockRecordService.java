package com.wealthshrimple.WealthShrimple.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wealthshrimple.WealthShrimple.dto.StockRecordData;
import com.wealthshrimple.WealthShrimple.entity.StockRecord;
import com.wealthshrimple.WealthShrimple.repository.StockRecordRepository;


@Service("stockRecordService")
public class DefaultStockRecordService implements StockRecordService{
	@Autowired
	private StockRecordRepository stockRecordRepository;
	
	@Override
	public StockRecordData saveStockRecord(StockRecordData StockRecord) {
		StockRecord StockRecordModel = populateStockRecordEntity(StockRecord);
		return populateStockRecordData(stockRecordRepository.save(StockRecordModel));
	}

	@Override
	public List<StockRecordData> getAllStockRecords() {
		 List<StockRecordData> StockRecords = new ArrayList<>();
	     List<StockRecord> StockRecordList = stockRecordRepository.findAll();
	     StockRecordList.forEach(StockRecord -> {
	            StockRecords.add(populateStockRecordData(StockRecord));
	     });
	     return StockRecords;
	}

	@Override
	public StockRecordData getStockRecordByTicker(String ticker) {
		return populateStockRecordData(stockRecordRepository.findByTicker(ticker));
	}

	@Override
	public List<StockRecordData> getStockRecordByUsername(String username) {
		List<StockRecordData> StockRecords = new ArrayList<>();
		List<StockRecord> StockRecordList = stockRecordRepository.findByUserName(username);
	     StockRecordList.forEach(StockRecord -> {
	            StockRecords.add(populateStockRecordData(StockRecord));
	     });
		return StockRecords;
	}
	
	private StockRecordData populateStockRecordData(final StockRecord stockRecord){
        StockRecordData stockRecordData = new StockRecordData();
        BeanUtils.copyProperties(stockRecord, stockRecordData);
        return stockRecordData;
    }
	
	private StockRecord populateStockRecordEntity(StockRecordData stockRecordData){
		StockRecord stockRecord = new StockRecord();
        stockRecord.setTicker(stockRecordData.getTicker());
        stockRecord.setPrice(stockRecordData.getPrice());
        stockRecord.setQuantity(stockRecordData.getQuantity());
        stockRecord.setUserName(stockRecordData.getUserName());
        return stockRecord;
    }

}
