package com.bzbala.ad.bazarbala.order.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Integer orderId;

	private String supplierId;

	private String customerId;

	private Double orderSubTotal;

	private String orderDate;

	private Double taxPercentange;

	private OrderStatus orderStatus;

	private String orderDtail;

	private PaymentMethod paymentMethod;

	private DeliveryMethod deliveryMethod;

	private String ShippingAddress;

	private String zipCode;

	private String shippingInstruction;

	private Integer shippingTeamId;

	private boolean deliveryCompleted;
	
    private String phoneNumber;
	
	private boolean isOkaytoCall;

	@OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_FK", referencedColumnName = "orderId")
	private List<OrderItem> orderItems;

	public OrderHistory() {

	}

	
   public OrderHistory(Integer orderId, String supplierId, String customerId, Double orderSubTotal, String orderDate,
			Double taxPercentange, OrderStatus orderStatus, String orderDtail, PaymentMethod paymentMethod,
			DeliveryMethod deliveryMethod, String shippingAddress, String zipCode, String shippingInstruction,
			Integer shippingTeamId, boolean deliveryCompleted, String phoneNumber, boolean isOkaytoCall,
			List<OrderItem> orderItems) {
		super();
		this.orderId = orderId;
		this.supplierId = supplierId;
		this.customerId = customerId;
		this.orderSubTotal = orderSubTotal;
		this.orderDate = orderDate;
		this.taxPercentange = taxPercentange;
		this.orderStatus = orderStatus;
		this.orderDtail = orderDtail;
		this.paymentMethod = paymentMethod;
		this.deliveryMethod = deliveryMethod;
		ShippingAddress = shippingAddress;
		this.zipCode = zipCode;
		this.shippingInstruction = shippingInstruction;
		this.shippingTeamId = shippingTeamId;
		this.deliveryCompleted = deliveryCompleted;
		this.phoneNumber = phoneNumber;
		this.isOkaytoCall = isOkaytoCall;
		this.orderItems = orderItems;
	}


    public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public String getOrderDtail() {
		return orderDtail;
	}

	public void setOrderDtail(String orderDtail) {
		this.orderDtail = orderDtail;
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

	public String getShippingInstruction() {
		return shippingInstruction;
	}

	public void setShippingInstruction(String shippingInstruction) {
		this.shippingInstruction = shippingInstruction;
	}

	public Integer getShippingTeamId() {
		return shippingTeamId;
	}

	public void setShippingTeamId(Integer shippingTeamId) {
		this.shippingTeamId = shippingTeamId;
	}

	public boolean isDeliveryCompleted() {
		return deliveryCompleted;
	}

	public void setDeliveryCompleted(boolean deliveryCompleted) {
		this.deliveryCompleted = deliveryCompleted;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
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
     
	
}
