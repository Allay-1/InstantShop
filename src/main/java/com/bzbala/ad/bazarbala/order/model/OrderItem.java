package com.bzbala.ad.bazarbala.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bzbala.ad.bazarbala.product.model.CurrencyType;
import com.bzbala.ad.bazarbala.product.model.Discount_Type;

@Entity
@Table(name = "OrderItem")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "itemId")
	private Integer itemId;

	private Integer orderId;

	private String productCode;

	private Integer quantity;

	private Double totalPrice;

	private CurrencyType currencyType;

	private Integer discount;

	private Discount_Type discountType;

	public OrderItem() {

	}

	public OrderItem(Integer orderId, String productCode, Integer quantity, Double totalPrice,
			CurrencyType currencyType, Integer discount, Discount_Type discountType) {
		super();
		this.orderId = orderId;
		this.productCode = productCode;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.currencyType = currencyType;
		this.discount = discount;
		this.discountType = discountType;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Discount_Type getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Discount_Type discountType) {
		this.discountType = discountType;
	}

}
