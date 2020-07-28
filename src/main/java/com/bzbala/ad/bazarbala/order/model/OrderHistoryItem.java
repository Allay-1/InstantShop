package com.bzbala.ad.bazarbala.order.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bzbala.ad.bazarbala.product.model.CurrencyType;
import com.bzbala.ad.bazarbala.product.model.DeliveryStatus;
import com.bzbala.ad.bazarbala.product.model.Discount_Type;
import com.bzbala.ad.bazarbala.product.model.Price;

@Entity
@Table(name = "OrderHistoryItem")
public class OrderHistoryItem implements Serializable {

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

	@Column(name = "customerId")
	private String customerId;

	@Column(name = "deliveryCompleted")
	private boolean deliveryCompleted;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "deliveryStatus")
	private DeliveryStatus deliveryStatus;

	@Column(name = "deliveryDateTime")
	private String deliveryDateTime;

	@Column(name = "shippingTeamId")
	private Integer shippingTeamId;

	
	public OrderHistoryItem() {

	}

	
   

	public OrderHistoryItem(int id, String orderId, String productCode, String productName, Integer quantity,
			Double totalPrice, CurrencyType currencyType, Integer discount, Double sellPrice,
			Discount_Type discountType, String supplierId, String customerId, boolean deliveryCompleted,
			DeliveryStatus deliveryStatus, String deliveryDateTime, Integer shippingTeamId) {
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
		this.customerId = customerId;
		this.deliveryCompleted = deliveryCompleted;
		this.deliveryStatus = deliveryStatus;
		this.deliveryDateTime = deliveryDateTime;
		this.shippingTeamId = shippingTeamId;
	}




	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}




	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}




	public String getDeliveryDateTime() {
		return deliveryDateTime;
	}




	public void setDeliveryDateTime(String deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
	}




	public Integer getShippingTeamId() {
		return shippingTeamId;
	}




	public void setShippingTeamId(Integer shippingTeamId) {
		this.shippingTeamId = shippingTeamId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Discount_Type getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Discount_Type discountType) {
		this.discountType = discountType;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public boolean isDeliveryCompleted() {
		return deliveryCompleted;
	}

	public void setDeliveryCompleted(boolean deliveryCompleted) {
		this.deliveryCompleted = deliveryCompleted;
	}


}
