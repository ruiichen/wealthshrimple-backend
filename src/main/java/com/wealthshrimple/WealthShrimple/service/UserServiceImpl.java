package com.wealthshrimple.WealthShrimple.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wealthshrimple.WealthShrimple.dto.ResponseBase;
import com.wealthshrimple.WealthShrimple.dto.StockRecordData;
import com.wealthshrimple.WealthShrimple.dto.UserData;
import com.wealthshrimple.WealthShrimple.entity.Datapoint;
import com.wealthshrimple.WealthShrimple.entity.StockRecord;
import com.wealthshrimple.WealthShrimple.entity.User;
import com.wealthshrimple.WealthShrimple.repository.StockRecordRepository;
import com.wealthshrimple.WealthShrimple.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service("userService")
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService{
	//Logger logger = LoggerFactory,getLogger(DefaultUserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StockRecordRepository stockRecordRepository;

	@Override
	public UserData saveUser(UserData user) {
		User userModel = populateUserEntity(user);
		return populateUserData(userRepository.save(userModel));
	}

	@Override
	public List<UserData> getAllUsers() {
		// TODO Auto-generated method stub
		 List<UserData> users = new ArrayList<>();
	     List<User> userList = userRepository.findAll();
	     userList.forEach(user -> {
	            users.add(populateUserData(user));
	     });
	     return users;
	}

	@Override
	public UserData getUserByUsername(String username) {
		return populateUserData(userRepository.findByUserName(username));
	}
	
	@Transactional
	public ResponseBase buyStocks(String username, StockRecordData stock) {
		ResponseBase resp = new ResponseBase();
		try {
			User target = userRepository.findByUserName(username);
			if (target != null) {
				target.setBalance(target.getBalance().subtract(stock.getPrice().multiply(new BigDecimal(stock.getQuantity()))));
				
				Datapoint newPoint = new Datapoint();
				newPoint.setAmount(target.getBalance());
				newPoint.setUserName(username);
				newPoint.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
				//System.out.println(newPoint.getDate());
				List<Datapoint> temp = target.getDatapoints();
				temp.add(newPoint);
				target.setDatapoints(temp);
				
				System.out.println(target.getBalance());
				List<StockRecord> records = target.getStockrecords();
				boolean add = true;
				for (StockRecord i : records) {
					if (i.getTicker().equalsIgnoreCase(stock.getTicker())) {
						i.setPrice(stock.getPrice());
						i.setQuantity(i.getQuantity()+stock.getQuantity());
						add = false;
						break;
					}
				}
				if (add) {
					records.add(populateStockRecordEntity(stock));
					target.setStockrecords(records);
				}
				resp.setCode("200");
				resp.setMessage("success");
			} else {
				resp.setCode("404");
				resp.setMessage("No user found");
				log.info("No user found: "+ username);
			}
		} catch (Exception e) {
			resp.setCode("500");
			resp.setMessage(e.getMessage());
			log.error("getUser error:"+e.getMessage(), e);
		}
		return resp;
	}
	
	@Transactional
	public ResponseBase sellStocks(String username, String ticker, int amount, BigDecimal price) {
		ResponseBase resp = new ResponseBase();
		resp.setCode("404");
		resp.setMessage("No stock found");
		try {
			User target = userRepository.findByUserName(username);
			if (target != null) {
				List<StockRecord> records = target.getStockrecords();
				for (StockRecord i : records) {
					if (i.getTicker().equalsIgnoreCase(ticker)) {
						i.setQuantity(i.getQuantity() - amount);
						target.setBalance(target.getBalance().add(price.multiply(new BigDecimal(amount))));
						
						Datapoint newPoint = new Datapoint();
						newPoint.setAmount(target.getBalance());
						newPoint.setUserName(username);
						newPoint.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
						//System.out.println(newPoint.getDate());
						List<Datapoint> temp = target.getDatapoints();
						temp.add(newPoint);
						target.setDatapoints(temp);
						
						if (i.getQuantity() == 0) {
							records.remove(i);
						}
						resp.setCode("200");
						resp.setMessage("success");
						break;
					}
				}
			} else {
				resp.setCode("404");
				resp.setMessage("No user found");
				log.info("No user found: "+ username);
			}
			
		} catch (Exception e) {
			resp.setCode("500");
			resp.setMessage(e.getMessage());
			log.error("getUser error:"+e.getMessage(), e);
		}
		return resp;
	}
	
	
	@Transactional(propagation = Propagation.NEVER)
	private UserData populateUserData(final User user){
		if (user == null)
			return null;
        UserData userData = new UserData();
        //System.out.println(user.getBalance());
        BeanUtils.copyProperties(user, userData);
        //System.out.println(userData.getBalance());
        if (user.getStockrecords() == null) {
        	System.out.println("the record list is null");
        } else {
        	user.getStockrecords().forEach(StockRecordData -> {
            	userData.getStockrecords().add(populateStockRecordData(StockRecordData));
            });
        }
        
        return  userData;
    }
	
	
	private User populateUserEntity(UserData UserData){
        User User = new User();
        User.setUserName(UserData.getUserName());
        User.setPassword(UserData.getPassword());
        User.setBalance(UserData.getBalance());
        User.setDatapoints(UserData.getDatapoints());
        return User;
    }
	
	private StockRecordData populateStockRecordData(final StockRecord stockRecord){
        StockRecordData stockRecordData = new StockRecordData();
        BeanUtils.copyProperties(stockRecord, stockRecordData);
        return stockRecordData;
    }
	
	private StockRecord populateStockRecordEntity(StockRecordData stockRecordData){
		StockRecord stockRecord = new StockRecord();
		BeanUtils.copyProperties(stockRecordData, stockRecord);
        return stockRecord;
    }
	

}
