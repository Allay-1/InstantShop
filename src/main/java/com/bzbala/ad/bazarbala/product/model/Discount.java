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
@Table(name = "Discount")
public class Discount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "productCode", nullable = false)
	private String productCode;

	@Column(name = "supplierId", nullable = false)
	private String supplierId;

	@Enumerated(EnumType.STRING)
	@Column(name = "typeOfDiscount")
	private Discount_Type typeOfDiscount;

	@Column(name = "discount")
	private String discount;

	@Column(name = "discountStartDate")
	private String discountStartDate;

	@Column(name = "discountEndDate")
	private String discountEndDate;

	public Discount() {

	}

	public Discount(String productCode, String supplierId, Discount_Type typeOfDiscount, String discount,
			String discountStartDate, String discountEndDate) {
		super();
		this.productCode = productCode;
		this.supplierId = supplierId;
		this.typeOfDiscount = typeOfDiscount;
		this.discount = discount;
		this.discountStartDate = discountStartDate;
		this.discountEndDate = discountEndDate;
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

	public Discount_Type getTypeOfDiscount() {
		return typeOfDiscount;
	}

	public void setTypeOfDiscount(Discount_Type typeOfDiscount) {
		this.typeOfDiscount = typeOfDiscount;
	}

	public String getDiscountStartDate() {
		return discountStartDate;
	}

	public void setDiscountStartDate(String discountStartDate) {
		this.discountStartDate = discountStartDate;
	}

	public String getDiscountEndDate() {
		return discountEndDate;
	}

	public void setDiscountEndDate(String discountEndDate) {
		this.discountEndDate = discountEndDate;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

}
