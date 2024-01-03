package com.wealthshrimple.WealthShrimple.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "datapoints")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Datapoint {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	@NotNull
	private String date;
	@NotNull
 	@Min(value= 0, message = "The value must be positive")
	private BigDecimal amount;
	@Size(min = 1, max = 254)
	@NotNull
	@Column(name="user_name")
	@JsonIgnore
	private String userName;
	
	//bi-directional many-to-one association to User
	@ManyToOne()
	@JoinColumn(name="user_name",insertable=false, updatable=false)
	@JsonIgnore
	private User user;
}
