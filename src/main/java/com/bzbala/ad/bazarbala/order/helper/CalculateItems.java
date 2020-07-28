package com.bzbala.ad.bazarbala.order.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bzbala.ad.bazarbala.order.model.CustomerOrder;
import com.bzbala.ad.bazarbala.order.model.Item;
import com.bzbala.ad.bazarbala.order.model.ItemRequest;
import com.bzbala.ad.bazarbala.order.model.ItemResponse;
import com.bzbala.ad.bazarbala.order.model.OrderHistory;
import com.bzbala.ad.bazarbala.order.model.OrderHistoryItem;
import com.bzbala.ad.bazarbala.order.model.OrderItem;
import com.bzbala.ad.bazarbala.product.model.ProductDetail;
import com.bzbala.ad.bazarbala.repository.Helper.ProductDetailRepository;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;

@Component
public class CalculateItems {
	@Autowired
	ProductDetailRepository productDetailRepository;
	
	@Autowired
	CalculateItemProcessor itemProcessor;
	
	/**
	 * 
	 * @param order
	 * @param request
	 * @param orderitems
	 * @param supplierId
	 * @param response
	 * @return
	 */
	public List<OrderItem> calculateItems(CustomerOrder order, ItemRequest request, List<OrderItem> orderitems,ItemResponse response) {
		if (request.getItems() != null && !request.getItems().isEmpty() && request.getItems().size() > 0) {
			List<Item> newItems = request.getItems();

			Map<String, String> itemAdded = new HashMap<>();
			for (Item newitem : newItems) {
				if (newitem.getActionType().equalsIgnoreCase("remove")) {
					orderitems=removeItem(orderitems, newitem);
					itemAdded.put(newitem.getProductCode(), "success");
				} else if (newitem.getActionType().equalsIgnoreCase("add")) {
					orderitems=removeItem(orderitems, newitem);
					calculateAddItem(order, orderitems, newitem, response);
				}
			}
		}

		return orderitems;

	}
	
	/**
	 * 
	 * @param order
	 * @param request
	 * @param orderitems
	 * @param response
	 * @return
	 */
	public List<OrderHistoryItem> calculateHistoryItems(OrderHistory order, ItemRequest request,
			List<OrderHistoryItem> orderitems, ItemResponse response) {
		if (request.getItems() != null && !request.getItems().isEmpty() && request.getItems().size() > 0) {
			List<Item> newItems = request.getItems();
			for (Item newitem : newItems) {
				if (newitem.getActionType().equalsIgnoreCase("add")) {
					calculateHistoryAddItem(order, orderitems, newitem, response);
				}
			}
		}

		return orderitems;

	}
	
	/**
	 * 
	 * @param order
	 * @param orderItems
	 * @param item
	 * @param supplierId
	 * @param response
	 * @return
	 */
	private List<OrderItem> calculateAddItem(CustomerOrder order, List<OrderItem> orderItems, Item item
			, ItemResponse response) {
		ProductDetail productDetail = null;
		//BazarbalaUtil.getOrderItem(item, supplierId);
		Optional<ProductDetail> productOptinalDetail = productDetailRepository.findById(item.getProductCode());
		if (productOptinalDetail.isPresent()) {
			productDetail = productOptinalDetail.get();
		}
		if (productDetail != null) {
			return itemProcessor.calculateProductPrice(order, orderItems, productDetail, item, response);
		}
		return orderItems;

	}
	
	/**
	 * 
	 * @param order
	 * @param orderitems
	 * @param item
	 * @param response
	 * @return
	 */
	private List<OrderHistoryItem> calculateHistoryAddItem(OrderHistory order, List<OrderHistoryItem> orderItems,
			Item item, ItemResponse response) {
		ProductDetail productDetail = null;
		// BazarbalaUtil.getOrderItem(item, supplierId);
		Optional<ProductDetail> productOptinalDetail = productDetailRepository.findById(item.getProductCode());
		if (productOptinalDetail.isPresent()) {
			productDetail = productOptinalDetail.get();
		}
		if (productDetail != null) {
			return itemProcessor.calculateHistoryProductPrice(order, orderItems, productDetail, item, response);
		}
		return orderItems;

	}
	
	/**
	 * 
	 * @param orderItems
	 * @param item
	 * @param supplierId
	 * @return
	 */
	private List<OrderItem> removeItem(List<OrderItem> orderItems, Item item) {
        if(orderItems !=null && orderItems.size()>0) {
		orderItems = orderItems.stream().filter(
				orderItem -> !orderItem.getProductCode().equalsIgnoreCase(item.getProductCode()))
				.collect(Collectors.toList());
        }
		return orderItems;
	}
	
	

}
