package com.wealthshrimple.WealthShrimple.dto;

import javax.validation.Valid;

import lombok.Data;

@Data
public class UserResponse extends ResponseBase {

	private static final long serialVersionUID = 1L;
	@Valid
	UserData user;
}
