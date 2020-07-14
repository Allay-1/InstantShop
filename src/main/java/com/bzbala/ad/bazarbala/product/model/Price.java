package com.bzbala.ad.bazarbala.product.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "Price")
public class Price implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum CurrencyType {
	    RS,
	    USD,
	    DOLLAR
	  }
	
	@Id
	@Column(name="productCode", nullable=false)
	private String productCode;
	
	@Column(name="supplierId", nullable=false)
	private String supplierId;
	
	@Column(name="basePrice")
	private Double basePrice;
	
	@Column(name="sellPrice")
	private Double sellPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(name="currencyType")
	private CurrencyType currencyType;
	
	
	public Price() {}

	

	public Price(String productCode, String supplierId, Double basePrice, Double sellPrice, CurrencyType currencyType) {
		super();
		this.productCode = productCode;
		this.supplierId = supplierId;
		this.basePrice = basePrice;
		this.sellPrice = sellPrice;
		this.currencyType = currencyType;
	}



	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	

	public Double getBasePrice() {
		return basePrice;
	}



	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}



	public Double getSellPrice() {
		return sellPrice;
	}



	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}



	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	
   
}
