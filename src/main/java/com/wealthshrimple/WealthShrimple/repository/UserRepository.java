package com.wealthshrimple.WealthShrimple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wealthshrimple.WealthShrimple.entity.StockRecord;
import com.wealthshrimple.WealthShrimple.entity.User;

public interface UserRepository extends JpaRepository <User, Long> {
	User findByUserName(String username);
	
	@Query(value ="select u FROM User u LEFT JOIN fetch u.stockrecords WHERE u.userName = :username")
	User findStockRecordWithUserName(String username);
	
	@Query(value ="select u FROM User u LEFT JOIN fetch u.datapoints WHERE u.userName = :username")
	User findDatapointWithUserName(String username);
}
