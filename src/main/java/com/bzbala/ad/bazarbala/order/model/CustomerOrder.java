package com.bzbala.ad.bazarbala.order.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bzbala.ad.bazarbala.product.model.OrderStatus;

@Entity
@Table(name = "CustomerOrder")
public class CustomerOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "orderId")
	private String orderId;

	@Column(name = "customerId")
	private String customerId;

	@Column(name = "orderSubTotal")
	private Double orderSubTotal;

	@Column(name = "orderDate")
	private String orderDate;

	@Column(name = "orderEndDate")
	private String orderEndDate;

	@Column(name = "lastUpdatedDate")
	private String lastUpdatedDate;

	@Column(name = "taxPercentange")
	private Double taxPercentange;

	@Enumerated(EnumType.STRING)
	@Column(name = "orderStatus")
	private OrderStatus orderStatus;


	@Enumerated(EnumType.STRING)
	@Column(name = "paymentMethod")
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(name = "deliveryMethod")
	private DeliveryMethod deliveryMethod;

	@Column(name = "ShippingAddress")
	private String ShippingAddress;

	@Column(name = "zipCode")
	private String zipCode;


	@Column(name = "phoneNumber")
	private String phoneNumber;

	@Column(name = "isOkaytoCall")
	private boolean isOkaytoCall;

	// @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, cascade =
	// CascadeType.ALL,orphanRemoval = true)
	// @JoinColumn(name = "orderId", referencedColumnName =
	// "orderId",insertable=false, updatable=false)
	@OneToMany(targetEntity = OrderItem.class,mappedBy = "orderId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems;

	public CustomerOrder() {

	}


    

	public CustomerOrder(String orderId, String customerId, Double orderSubTotal, String orderDate, String orderEndDate,
			String lastUpdatedDate, Double taxPercentange, OrderStatus orderStatus, PaymentMethod paymentMethod,
			DeliveryMethod deliveryMethod, String shippingAddress, String zipCode, String phoneNumber,
			boolean isOkaytoCall, List<OrderItem> orderItems) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderSubTotal = orderSubTotal;
		this.orderDate = orderDate;
		this.orderEndDate = orderEndDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.taxPercentange = taxPercentange;
		this.orderStatus = orderStatus;
		this.paymentMethod = paymentMethod;
		this.deliveryMethod = deliveryMethod;
		ShippingAddress = shippingAddress;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
		this.isOkaytoCall = isOkaytoCall;
		this.orderItems = orderItems;
	}




	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getOrderSubTotal() {
		return orderSubTotal;
	}

	public void setOrderSubTotal(Double orderSubTotal) {
		this.orderSubTotal = orderSubTotal;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Double getTaxPercentange() {
		return taxPercentange;
	}

	public void setTaxPercentange(Double taxPercentange) {
		this.taxPercentange = taxPercentange;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getShippingAddress() {
		return ShippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		ShippingAddress = shippingAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isOkaytoCall() {
		return isOkaytoCall;
	}

	public void setOkaytoCall(boolean isOkaytoCall) {
		this.isOkaytoCall = isOkaytoCall;
	}

	public String getOrderEndDate() {
		return orderEndDate;
	}

	public void setOrderEndDate(String orderEndDate) {
		this.orderEndDate = orderEndDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}
