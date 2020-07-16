package com.bzbala.ad.bazarbala.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bzbala.ad.bazarbala.product.model.DeliveryStatus;

@Entity
@Table(name = "OrderItem")
public class Delivery implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "deliveryId")
	private Integer deliveryId;

	private String customerId;

	private String supplierId;

	private DeliveryStatus deliveryStatus;

	private Integer orderId;

	private String deliveryDateTime;

	private PaymentMethod paymentMethod;

	private DeliveryMethod deliveryMethod;

	private String ShippingAddress;

	private String shippingInstruction;

	private Integer shippingTeamId;

	public Delivery() {

	}

	public Delivery(Integer deliveryId, String customerId, String supplierId, DeliveryStatus deliveryStatus,
			Integer orderId, String deliveryDateTime, PaymentMethod paymentMethod, DeliveryMethod deliveryMethod,
			String shippingAddress, String shippingInstruction, Integer shippingTeamId) {
		super();
		this.deliveryId = deliveryId;
		this.customerId = customerId;
		this.supplierId = supplierId;
		this.deliveryStatus = deliveryStatus;
		this.orderId = orderId;
		this.deliveryDateTime = deliveryDateTime;
		this.paymentMethod = paymentMethod;
		this.deliveryMethod = deliveryMethod;
		ShippingAddress = shippingAddress;
		this.shippingInstruction = shippingInstruction;
		this.shippingTeamId = shippingTeamId;
	}

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getDeliveryDateTime() {
		return deliveryDateTime;
	}

	public void setDeliveryDateTime(String deliveryDateTime) {
		this.deliveryDateTime = deliveryDateTime;
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

}
