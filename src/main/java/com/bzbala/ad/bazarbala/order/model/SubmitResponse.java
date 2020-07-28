package com.bzbala.ad.bazarbala.order.model;

import java.util.Map;

public class SubmitResponse {

	private OrderHistory customerOrder;

	Map<String, String> itemsAdded;

	Map<String, String> itemFailToAdd;

	
    

	public OrderHistory getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(OrderHistory customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Map<String, String> getItemsAdded() {
		return itemsAdded;
	}

	public void setItemsAdded(Map<String, String> itemsAdded) {
		this.itemsAdded = itemsAdded;
	}

	public Map<String, String> getItemFailToAdd() {
		return itemFailToAdd;
	}

	public void setItemFailToAdd(Map<String, String> itemFailToAdd) {
		this.itemFailToAdd = itemFailToAdd;
	}

}
