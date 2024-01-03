package com.wealthshrimple.WealthShrimple.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseBase implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;

}
