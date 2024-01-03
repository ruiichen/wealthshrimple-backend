package com.wealthshrimple.WealthShrimple.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wealthshrimple.WealthShrimple.dto.ResponseBase;
import com.wealthshrimple.WealthShrimple.dto.StockRecordData;
import com.wealthshrimple.WealthShrimple.dto.UserData;
import com.wealthshrimple.WealthShrimple.dto.UserResponse;
import com.wealthshrimple.WealthShrimple.entity.Datapoint;
import com.wealthshrimple.WealthShrimple.service.IUserService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
//@CrossOrigin
public class UserController {

	@Resource(name = "userService")
	private IUserService userService;
	
	//@CrossOrigin
	@GetMapping(path = "/guh")
	@ResponseBody
	public String user() {
		return "hello world";
	}
	
	//@CrossOrigin
	@GetMapping(path="/login")
	@ResponseBody
	public UserResponse login (JwtAuthenticationToken token) {
		UserResponse resp = new UserResponse();
		String username = token.getTokenAttributes().get("email").toString();
		String password = token.getTokenAttributes().get("given_name").toString();
		//System.out.println(username + password);
		try {
			UserData search = userService.getUserByUsername(username);
			if (search == null) {
				UserData userData = new UserData();
				userData.setBalance(new BigDecimal("1000"));
				userData.setStockrecords(null);
				userData.setUserName(username);
				userData.setPassword(password);
				System.out.println(userData.getUserName());

				Datapoint newPoint = new Datapoint();
				newPoint.setAmount(new BigDecimal("1000"));
				newPoint.setUserName(username);
				newPoint.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
				// System.out.println(newPoint.getDate());
				List<Datapoint> temp = new ArrayList<>();
				temp.add(newPoint);
				// System.out.println(temp);

				userData.setDatapoints(temp);
				UserData returnData = userService.saveUser(userData);
				resp.setUser(returnData);
				resp.setCode("200");
				resp.setMessage("success");
			} else {
				resp.setCode("2001");
				resp.setMessage("The user already exists");
				resp.setUser(search);			}
		} catch (Exception e) {
			resp.setCode("500");
			resp.setMessage(e.getMessage());
			log.error("getUser error:" + e.getMessage(), e);
		}
		return resp;
		
	}

//	@GetMapping
//	public List<UserData> getUsers(){
//		return userService.getAllUsers();
//	}

	@CrossOrigin
	@PostMapping(path = "/sell/{ticker}/{amount}/{price}")
	public ResponseBase sellStocks(JwtAuthenticationToken token, @PathVariable String ticker, @PathVariable int amount,
			@PathVariable BigDecimal price) {
		String username = token.getTokenAttributes().get("email").toString();
		return userService.sellStocks(username, ticker, amount, price);
	}

	@CrossOrigin
	@PostMapping(path = "/buy")
	public ResponseBase buyStocks(JwtAuthenticationToken token, @RequestBody StockRecordData stockRecordData) {
		String username = token.getTokenAttributes().get("email").toString();
		stockRecordData.setUserName(username);
		return userService.buyStocks(username, stockRecordData);
	}

	@CrossOrigin
	@GetMapping(path = "/user/find", produces = { MediaType.APPLICATION_JSON_VALUE })
	public UserResponse getUser(JwtAuthenticationToken token) {
		log.info("get user begin...");
		String username = token.getTokenAttributes().get("email").toString();
		UserResponse resp = new UserResponse();
		try {
			UserData userData = userService.getUserByUsername(username);
			// System.out.println(userData.getBalance());
			if (userData != null) {
				resp.setUser(userData);
				resp.setCode("200");
				resp.setMessage("success");
			} else {
				resp.setCode("404");
				resp.setMessage("No user found");
				log.info("No user found: " + username);
			}
		} catch (Exception e) {
			resp.setCode("500");
			resp.setMessage(e.getMessage());
			log.error("getUser error:" + e.getMessage(), e);
			// throw new ResourceNotFoundException(username);
		}
		return resp;
	}

	@CrossOrigin
	@PostMapping(path = "/add/{username}/{password}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "test", response = ResponseBase.class)
	public UserResponse createUser(@PathVariable String username, @PathVariable String password) {
		UserResponse resp = new UserResponse();
		try {
			UserData search = userService.getUserByUsername(username);
			if (search == null) {
				UserData userData = new UserData();
				userData.setBalance(new BigDecimal("1000"));
				userData.setStockrecords(null);
				userData.setUserName(username);
				userData.setPassword(password);

				Datapoint newPoint = new Datapoint();
				newPoint.setAmount(new BigDecimal("1000"));
				newPoint.setUserName(username);
				newPoint.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
				// System.out.println(newPoint.getDate());
				List<Datapoint> temp = new ArrayList<>();
				temp.add(newPoint);
				// System.out.println(temp);

				userData.setDatapoints(temp);
				UserData returnData = userService.saveUser(userData);
				resp.setUser(returnData);
				resp.setCode("200");
				resp.setMessage("success");
			} else {
				resp.setCode("404");
				resp.setMessage("The user already exists");
			}
		} catch (Exception e) {
			resp.setCode("500");
			resp.setMessage(e.getMessage());
			log.error("getUser error:" + e.getMessage(), e);
		}
		return resp;
	}

}
