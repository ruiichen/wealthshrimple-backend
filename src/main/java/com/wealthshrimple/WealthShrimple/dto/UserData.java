package com.wealthshrimple.WealthShrimple.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wealthshrimple.WealthShrimple.entity.Datapoint;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserData implements Serializable{

    private static final long serialVersionUID = 1L;
    @NotNull
    @Size(min=1, max=254)
	private String userName;
    @NotNull
    @Size(min=1, max=20)
    private String password;
    @NotNull
    @Min(value= 0, message = "The value must be positive")
    private BigDecimal balance;
    private List<Datapoint> datapoints;
    private List<StockRecordData> stockrecords = new ArrayList<>();

    public UserData() {
    }
    
}
