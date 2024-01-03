package com.wealthshrimple.WealthShrimple.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Query;

import lombok.Data;
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Data
public class User {
	 	@Id
	 	@Size(min = 1, max = 254)
	    private String userName;
	 	@Size(min = 1, max = 20)
	 	@NotNull
	 	@Size(min = 1, max = 20)
	    private String password;
	 	@NotNull
	 	@Min(value= 0, message = "The value must be positive")
	    private BigDecimal balance;
	 	
	 	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval=true)
	 	private List<Datapoint> datapoints;
	 	
	 	//bi-directional many-to-one association to Stockrecord
		@OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval=true)
		private List<StockRecord> stockrecords;

}
