package com.bzbala.ad.bazarbala.order.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bzbala.ad.bazarbala.order.model.CustomerOrder;
import com.bzbala.ad.bazarbala.order.model.Delivery;
import com.bzbala.ad.bazarbala.order.model.DeliveryMethod;
import com.bzbala.ad.bazarbala.order.model.ItemRequest;
import com.bzbala.ad.bazarbala.order.model.ItemResponse;
import com.bzbala.ad.bazarbala.order.model.OrderHistory;
import com.bzbala.ad.bazarbala.product.model.DeliveryStatus;
import com.bzbala.ad.bazarbala.product.model.OrderStatus;
import com.bzbala.ad.bazarbala.product.model.ProductDetail;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;
import  com.bzbala.ad.bazarbala.order.model.OrderHistoryItem;
import com.bzbala.ad.bazarbala.order.model.PaymentMethod;
import com.bzbala.ad.bazarbala.order.repository.OrderHistoryRepository;

@Component
public class SubmitOrderHelper {
	
	@Autowired
	CreateOrUpdateOrderItems createOrUpdateOrderItems;
	
	@Autowired
	OrderHistoryRepository orderHistoryRepository;
	
	public ItemResponse submitOrder(CustomerOrder customerOrder) {
		OrderHistory orderHistory = new OrderHistory();
		
		
		createOrderHistory(customerOrder,orderHistory);
		ItemRequest itemRequest=BazarbalaUtil.createItemRequest(customerOrder);
		ItemResponse response = new ItemResponse();
		List<OrderHistoryItem> orderHistoryItems=createOrUpdateOrderItems.createOrderHistoryItem(orderHistory, itemRequest, response);
		orderHistory.setOrderItems(orderHistoryItems);
		BazarbalaUtil.processHistoryCustomerOrder(orderHistory);
		orderHistory.setOrderStatus(OrderStatus.valueOf("SUBMITTED"));
		orderHistoryRepository.save(orderHistory);
		response.setOrderHistory(orderHistory);
		response.setCustomerOrder(null);
		return response;
	}
	
	private OrderHistory createOrderHistory(CustomerOrder customerOrder, OrderHistory orderHistory) {

		orderHistory.setCustomerId(customerOrder.getCustomerId());
		orderHistory.setDeliveryCompleted(false);
		orderHistory.setDeliveryMethod(DeliveryMethod.valueOf(customerOrder.getDeliveryMethod().toString()));
		orderHistory.setOkaytoCall(customerOrder.isOkaytoCall());
		orderHistory.setOrderDate(customerOrder.getOrderDate());
		orderHistory.setOrderId(customerOrder.getOrderId());
		orderHistory.setOrderStatus(OrderStatus.valueOf(customerOrder.getOrderStatus().toString()));
		orderHistory.setPaymentMethod(PaymentMethod.valueOf(customerOrder.getPaymentMethod().toString()));
		orderHistory.setPhoneNumber(customerOrder.getPhoneNumber());
		orderHistory.setTaxPercentange(customerOrder.getTaxPercentange());
		orderHistory.setOrderDate(BazarbalaUtil.getCurrentDate().toString());
		orderHistory.setZipCode(customerOrder.getZipCode());
		orderHistory.setTaxPercentange(Double.valueOf(1));

		return orderHistory;

	}
	
	public List<OrderHistory> findOrderHistory(String supplierId) {
		List<OrderHistory> orderHistoryList = new ArrayList<>();
		orderHistoryRepository.findAll().forEach(orderHistoryList::add);
		orderHistoryList.stream().map(order -> {
            List<OrderHistoryItem> items = order.getOrderItems().stream()
					.filter(lineItem -> lineItem.getSupplierId().equalsIgnoreCase(supplierId))
					.collect(Collectors.toList());
			order.setOrderItems(items);
			BazarbalaUtil.processHistoryCustomerOrder(order);
			return order;
		}).collect(Collectors.toList());
		
		return orderHistoryList;
	}
	
	public List<OrderHistory> findCustomerOrderHistory(String cusotmerId) {
		List<OrderHistory> orderHistoryList = new ArrayList<>();
		orderHistoryRepository.findAllByCustomerId(cusotmerId).forEach(orderHistoryList::add);
		return orderHistoryList;
	}

}
