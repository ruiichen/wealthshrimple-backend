package com.wealthshrimple.WealthShrimple.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StockRecordData implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private Long id;
	private String ticker;
    private Integer quantity;
    private BigDecimal price;
    @JsonIgnore
    private String userName;
    @JsonIgnore
    private UserData user;
    
    public StockRecordData() {
    	
    }

}
