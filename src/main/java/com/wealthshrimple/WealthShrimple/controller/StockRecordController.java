package com.wealthshrimple.WealthShrimple.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wealthshrimple.WealthShrimple.dto.StockRecordData;
import com.wealthshrimple.WealthShrimple.service.StockRecordService;

@RestController
@RequestMapping("/stockrecords")
public class StockRecordController {
	
	@Resource(name = "stockRecordService")
	private StockRecordService stockRecordService;
	
	@GetMapping
	public List<StockRecordData> getStockRecords(){
		return stockRecordService.getAllStockRecords();
	}
	
	@GetMapping("/stockrecord/{ticker}")
	public StockRecordData getStockRecord(@PathVariable String ticker) {
		return stockRecordService.getStockRecordByTicker(ticker);
	}
	
	@PostMapping("/stockrecord")
	public StockRecordData getStockRecord(final @RequestBody StockRecordData stockRecordData) {
		return stockRecordService.saveStockRecord(stockRecordData);
	}
	

}
