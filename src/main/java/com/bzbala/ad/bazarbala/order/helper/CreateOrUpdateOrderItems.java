package com.bzbala.ad.bazarbala.order.helper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bzbala.ad.bazarbala.order.model.CustomerOrder;
import com.bzbala.ad.bazarbala.order.model.ItemRequest;
import com.bzbala.ad.bazarbala.order.model.ItemResponse;
import com.bzbala.ad.bazarbala.order.model.OrderHistory;
import com.bzbala.ad.bazarbala.order.model.OrderHistoryItem;
import com.bzbala.ad.bazarbala.order.model.OrderItem;

@Component
public class CreateOrUpdateOrderItems {
	
	@Autowired
	CalculateItems calculateItems;
	
	/**
	 * 
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 */
	public List<OrderItem> createOrUpdateOrderItem(CustomerOrder order, ItemRequest request, ItemResponse response) {

		List<OrderItem> orderItemsNewList = new ArrayList<>();

		List<OrderItem> orderItems = order.getOrderItems();
		//String supplierId = request.getSupplierId();
		if (orderItems != null) {
			order.getOrderItems().stream().forEach(orderItemsNewList::add);
			order.getOrderItems().clear();
		}else {
			orderItems=new ArrayList<>();
		}

		orderItemsNewList=calculateItems.calculateItems(order, request, orderItemsNewList, response);
		if(order.getOrderItems()!=null) {
		order.getOrderItems().clear();
		order.getOrderItems().addAll(orderItemsNewList);
		}else {
			order.setOrderItems(orderItemsNewList);
		}
		return orderItemsNewList;

	}
	
	/**
	 * 
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 */
	public List<OrderHistoryItem> createOrderHistoryItem(OrderHistory order, ItemRequest request,
			ItemResponse response) {

		List<OrderHistoryItem> orderItemsNewList = new ArrayList<>();

		orderItemsNewList = calculateItems.calculateHistoryItems(order, request, orderItemsNewList, response);
		
		order.setOrderItems(orderItemsNewList);

		return orderItemsNewList;

	}

}
