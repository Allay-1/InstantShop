package com.bzbala.ad.bazarbala.order.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import com.bzbala.ad.bazarbala.product.model.DeliveryStatus;

@Entity
@Table(name = "Delivery")
public class Delivery implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	int id;
	
	@Column(name = "orderId")
	private String orderId;

	@Enumerated(EnumType.STRING)
	@Column(name = "deliveryStatus")
	private DeliveryStatus deliveryStatus;

	@Column(name = "deliveryDateTime")
	private String deliveryDateTime;

	@Column(name = "shippingTeamId")
	private Integer shippingTeamId;

	public Delivery() {

	}

	
    

	public Delivery(int id, String orderId, DeliveryStatus deliveryStatus, String deliveryDateTime,
			Integer shippingTeamId) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.deliveryStatus = deliveryStatus;
		this.deliveryDateTime = deliveryDateTime;
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

}
