package com.bzbala.ad.bazarbala.order.model;

import java.util.Map;

public class ItemResponse {

	private CustomerOrder customerOrder;
	
	private OrderHistory orderHistory;

	Map<String, String> itemsAdded;

	Map<String, String> itemFailToAdd;

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
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

	public OrderHistory getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(OrderHistory orderHistory) {
		this.orderHistory = orderHistory;
	}
   
}
