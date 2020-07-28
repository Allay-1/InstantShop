package com.bzbala.ad.bazarbala.order.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bzbala.ad.bazarbala.order.model.CustomerOrder;
import com.bzbala.ad.bazarbala.order.model.Item;
import com.bzbala.ad.bazarbala.order.model.ItemResponse;
import com.bzbala.ad.bazarbala.order.model.OrderHistory;
import com.bzbala.ad.bazarbala.order.model.OrderHistoryItem;
import com.bzbala.ad.bazarbala.order.model.OrderItem;
import com.bzbala.ad.bazarbala.product.model.CurrencyType;
import com.bzbala.ad.bazarbala.product.model.DeliveryStatus;
import com.bzbala.ad.bazarbala.product.model.Discount;
import com.bzbala.ad.bazarbala.product.model.Price;
import com.bzbala.ad.bazarbala.product.model.ProductDetail;
import com.bzbala.ad.bazarbala.repository.Helper.ProductDetailRepository;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;

@Component
public class CalculateItemProcessor {
	
	@Autowired
	DiscountCalculator discountCalculator;
	
	@Autowired
	ProductDetailRepository productDetailRepository;
	
	/**
	 * 
	 * @param order
	 * @param orderItems
	 * @param productDetail
	 * @param item
	 * @param response
	 * @return
	 */
	public List<OrderItem> calculateProductPrice(CustomerOrder order, List<OrderItem> orderItems,
			ProductDetail productDetail, Item item, ItemResponse response) {
		Map<String, String> itemFailedToAdd = new HashMap<>();
		Map<String, String> itemToAdd = new HashMap<>();
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(order.getOrderId());

		boolean stock = Boolean.valueOf(productDetail.getStock());

		Price price = productDetail.getPrice();

		Discount discount = productDetail.getDiscount();

		if (stock && item.getQuantity() <= productDetail.getQuantity()) {
			try {
				priceCalculation(price, discount, item.getQuantity(), orderItem,productDetail);
				if (orderItem.getQuantity() <= productDetail.getQuantity()) {
					orderItems.add(orderItem);
				} else {
					itemFailedToAdd.put(item.getProductCode(), "Failed to add product is now out of stock");
				}
				if (response.getItemsAdded() != null) {
					response.getItemsAdded().put(orderItem.getProductCode(), "Success");
				} else {
					itemToAdd.put(orderItem.getProductCode(), "Success");
					response.setItemsAdded(itemToAdd);
				}
			} catch (Exception e) {
				itemFailedToAdd.put(item.getProductCode(), e.getMessage());
			}
		} else {
			itemFailedToAdd.put(item.getProductCode(), "Failed to add product is now out of stock");
		}

		response.setItemFailToAdd(itemFailedToAdd);
		return orderItems;

	}
	
	public List<OrderHistoryItem> calculateHistoryProductPrice(OrderHistory order, List<OrderHistoryItem> orderItems,
			ProductDetail productDetail, Item item, ItemResponse response) {
		Map<String, String> itemFailedToAdd = new HashMap<>();
		Map<String, String> itemToAdd = new HashMap<>();
		OrderHistoryItem orderItem = new OrderHistoryItem();
		orderItem.setOrderId(order.getOrderId());

		boolean stock = Boolean.valueOf(productDetail.getStock());

		Price price = productDetail.getPrice();

		Discount discount = productDetail.getDiscount();

		if (stock && item.getQuantity() <= productDetail.getQuantity()) {
			try {
				priceHistoryCalculation(price, discount, item.getQuantity(), orderItem,productDetail,order.getCustomerId());
				if (orderItem.getQuantity() <= productDetail.getQuantity()) {
					orderItems.add(orderItem);
				} else {
					itemFailedToAdd.put(item.getProductCode(), "Failed to add product is now out of stock");
				}
				if (response.getItemsAdded() != null) {
					response.getItemsAdded().put(orderItem.getProductCode(), "Success");
				} else {
					itemToAdd.put(orderItem.getProductCode(), "Success");
					response.setItemsAdded(itemToAdd);
				}
			} catch (Exception e) {
				itemFailedToAdd.put(item.getProductCode(), e.getMessage());
			}
		} else {
			itemFailedToAdd.put(item.getProductCode(), "Failed to add product is now out of stock");
		}

		response.setItemFailToAdd(itemFailedToAdd);
		return orderItems ;

	}
	
	/**
	 * 
	 * @param price
	 * @param discount
	 * @param quantity
	 * @param orderItem
	 * @throws Exception
	 */
	private void priceCalculation(Price price, Discount discount, Integer quantity, OrderItem orderItem,ProductDetail productDetai)
			throws Exception {

		orderItem.setCurrencyType(CurrencyType.valueOf(price.getCurrencyType().toString()));
		orderItem.setTotalPrice(price.getSellPrice() * quantity);
		orderItem.setQuantity(quantity);
		orderItem.setProductCode(price.getProductCode());
		orderItem.setSellPrice(price.getSellPrice());
		orderItem.setProductName(productDetai.getName());
		orderItem.setSupplierId(BazarbalaUtil.getSupplierCode(price.getProductCode()));
		
		discountCalculator.calcualteDiscount(orderItem, discount, price);

	}
	
	/**
	 * 
	 * @param price
	 * @param discount
	 * @param quantity
	 * @param orderItem
	 * @throws Exception
	 */
	private void priceHistoryCalculation(Price price, Discount discount, Integer quantity, OrderHistoryItem orderItem,ProductDetail productDetai,String customerId)
			throws Exception {

		orderItem.setCurrencyType(CurrencyType.valueOf(price.getCurrencyType().toString()));
		orderItem.setTotalPrice(price.getSellPrice() * quantity);
		orderItem.setQuantity(quantity);
		orderItem.setProductCode(price.getProductCode());
		orderItem.setSellPrice(price.getSellPrice());
		orderItem.setProductName(productDetai.getName());
		orderItem.setSupplierId(BazarbalaUtil.getSupplierCode(price.getProductCode()));
		orderItem.setCustomerId(customerId);
		orderItem.setDeliveryCompleted(false);
		orderItem.setDeliveryStatus(DeliveryStatus.valueOf("INPROGRESS"));
		orderItem.setDeliveryDateTime(null);
		orderItem.setShippingTeamId(null);
		discountCalculator.calcualteHistoryDiscount(orderItem, discount, price);

	}

}
