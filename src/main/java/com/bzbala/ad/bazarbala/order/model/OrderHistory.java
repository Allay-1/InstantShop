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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.bzbala.ad.bazarbala.product.model.OrderStatus;

@Entity
@Table(name = "OrderHistory")
public class OrderHistory implements Serializable {

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

	@Enumerated(EnumType.STRING)
	@Column(name = "orderStatus")
	private OrderStatus orderStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "paymentMethod")
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	@Column(name = "deliveryMethod")
	private DeliveryMethod deliveryMethod;

	@Column(name = "taxPercentange")
	private Double taxPercentange;

	@Column(name = "deliveryCompleted")
	private boolean deliveryCompleted;

	@Column(name = "phoneNumber")
	private String phoneNumber;

	@Column(name = "isOkaytoCall")
	private boolean isOkaytoCall;

	@Column(name = "zipCode")
	private String zipCode;
	
	@Column(name = "ShippingAddress")
	private String ShippingAddress;

	public OrderHistory() {

	}

	@OneToMany(targetEntity = OrderHistoryItem.class, mappedBy = "orderId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderHistoryItem> orderItems;

	
  public OrderHistory(String orderId, String customerId, Double orderSubTotal, String orderDate,
			OrderStatus orderStatus, PaymentMethod paymentMethod, DeliveryMethod deliveryMethod, Double taxPercentange,
			boolean deliveryCompleted, String phoneNumber, boolean isOkaytoCall, String zipCode, String shippingAddress,
			List<OrderHistoryItem> orderItems) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderSubTotal = orderSubTotal;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.paymentMethod = paymentMethod;
		this.deliveryMethod = deliveryMethod;
		this.taxPercentange = taxPercentange;
		this.deliveryCompleted = deliveryCompleted;
		this.phoneNumber = phoneNumber;
		this.isOkaytoCall = isOkaytoCall;
		this.zipCode = zipCode;
		ShippingAddress = shippingAddress;
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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

	public Double getTaxPercentange() {
		return taxPercentange;
	}

	public void setTaxPercentange(Double taxPercentange) {
		this.taxPercentange = taxPercentange;
	}

	public boolean isDeliveryCompleted() {
		return deliveryCompleted;
	}

	public void setDeliveryCompleted(boolean deliveryCompleted) {
		this.deliveryCompleted = deliveryCompleted;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<OrderHistoryItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderHistoryItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getShippingAddress() {
		return ShippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		ShippingAddress = shippingAddress;
	}
	
	

}
