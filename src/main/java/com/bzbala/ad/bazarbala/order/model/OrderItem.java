package com.bzbala.ad.bazarbala.order.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import com.bzbala.ad.bazarbala.product.model.CurrencyType;
import com.bzbala.ad.bazarbala.product.model.Discount_Type;

@Entity
@Table(name = "OrderItem")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name = "orderId", nullable = false)
	private String orderId;

	@Column(name = "productCode")
	private String productCode;

	@Column(name = "productName")
	private String productName;

	@Column(name = "quantity")
	private Integer quantity;
    
	@Column(name = "totalPrice")
	private Double totalPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "currencyType")
	private CurrencyType currencyType;

	@Column(name = "discount")
	private Integer discount;

	@Column(name = "sellPrice")
	private Double sellPrice;

	@Enumerated(EnumType.STRING)
	@Column(name = "discountType")
	private Discount_Type discountType;

	@Column(name = "supplierId")
	private String supplierId;

	// @ManyToOne(targetEntity = CustomerOrder.class,fetch = FetchType.EAGER)
	// @JoinColumn(name = "orderId",referencedColumnName="orderId",insertable=false,
	// updatable=false)
	// private CustomerOrder customerOrder;

	public OrderItem() {

	}

	public OrderItem(int id, String orderId, String productCode, String productName, Integer quantity,
			Double totalPrice, CurrencyType currencyType, Integer discount, Double sellPrice,
			Discount_Type discountType, String supplierId) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.productCode = productCode;
		this.productName = productName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.currencyType = currencyType;
		this.discount = discount;
		this.sellPrice = sellPrice;
		this.discountType = discountType;
		this.supplierId = supplierId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
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
