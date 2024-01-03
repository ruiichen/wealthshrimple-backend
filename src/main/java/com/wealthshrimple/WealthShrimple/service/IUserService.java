package com.wealthshrimple.WealthShrimple.service;

import java.math.BigDecimal;
import java.util.List;

import com.wealthshrimple.WealthShrimple.dto.ResponseBase;
import com.wealthshrimple.WealthShrimple.dto.StockRecordData;
import com.wealthshrimple.WealthShrimple.dto.UserData;

public interface IUserService {
	UserData saveUser(UserData user);
	List<UserData> getAllUsers();
	UserData getUserByUsername(String username);
	ResponseBase buyStocks(String username, StockRecordData stockRecordData);
	ResponseBase sellStocks(String username, String ticker, int amount, BigDecimal price);
}
