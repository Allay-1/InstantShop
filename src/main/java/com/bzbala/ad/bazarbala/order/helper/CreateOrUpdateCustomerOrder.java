package com.bzbala.ad.bazarbala.order.helper;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bzbala.ad.bazarbala.dao.DBServices;
import com.bzbala.ad.bazarbala.dao.PersonalInfo;
import com.bzbala.ad.bazarbala.order.model.CustomerOrder;
import com.bzbala.ad.bazarbala.order.model.DeliveryMethod;
import com.bzbala.ad.bazarbala.order.model.ItemRequest;
import com.bzbala.ad.bazarbala.order.model.ItemResponse;
import com.bzbala.ad.bazarbala.order.model.OrderItem;
import com.bzbala.ad.bazarbala.order.model.OrderResponse;
import com.bzbala.ad.bazarbala.order.model.PaymentMethod;
import com.bzbala.ad.bazarbala.order.model.UpdateOrderRequest;
import com.bzbala.ad.bazarbala.order.repository.CustomerOrderRepository;
import com.bzbala.ad.bazarbala.product.model.OrderStatus;
import com.bzbala.ad.bazarbala.product.model.ProductDetail;
import com.bzbala.ad.bazarbala.repository.Helper.ProductDetailRepository;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;

@Component
public class CreateOrUpdateCustomerOrder {
	
	@Autowired
	CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	CalculateItems calculateItems;
	
	@Autowired
	CreateOrUpdateOrderItems createOrUpdateOrderItems;
	
	@Autowired
	SubmitOrderHelper submitOrderHelper;
	
	@Autowired
	DBServices datebaseService;
	
	@Autowired
	ProductDetailRepository productDetailRepository;
	
	public OrderResponse getSupplierHistoryOrder(String supplierId) {
		OrderResponse order = new OrderResponse();
		order.setOrders(submitOrderHelper.findOrderHistory(supplierId));
		return order;
	}
	
	public OrderResponse getCustomerHistoryOrder(String customerId) {
		OrderResponse order = new OrderResponse();
		order.setOrders(submitOrderHelper.findCustomerOrderHistory(customerId));
		return order;
	}
	
	public ItemResponse updateHistoryOrder(UpdateOrderRequest updateOrderRequest, HttpServletRequest request,
			HttpServletResponse response, ItemResponse itemResponse) {

		String bin = BazarbalaUtil.genrateBalaJiBIN(request, response);
		CustomerOrder customerOrder = null;
		if (bin != null) {
			Optional<CustomerOrder> customerOptionalOrder = customerOrderRepository.findById(bin);

			if (customerOptionalOrder.isPresent()) {
				customerOrder = customerOptionalOrder.get();
				HttpSession session = request.getSession(true);
				
				PersonalInfo personalInfo=(PersonalInfo) session.getAttribute("PersonaInfo");
				if(personalInfo != null && personalInfo.getPersonalId() != null && customerOrder != null) {
					customerOrder.setCustomerId(personalInfo.getPersonalId());
				}
				customerOrder.setLastUpdatedDate(BazarbalaUtil.getCurrentDate().toString());
				itemResponse.setCustomerOrder(customerOrder);
				if (updateOrderRequest.getTypeOfRequest() != null && !updateOrderRequest.getTypeOfRequest().isEmpty()
						&& updateOrderRequest.getTypeOfRequest().equalsIgnoreCase("updateAndEdit")) {
					createOrUpdateOrderItems.createOrUpdateOrderItem(customerOrder, updateOrderRequest.getItemRequest(),
							itemResponse);
					updateCustomerFields(customerOrder, updateOrderRequest, false);
					customerOrder.setOrderStatus(OrderStatus.valueOf("COMPLETE"));
				} else if (updateOrderRequest.getTypeOfRequest() != null
						&& !updateOrderRequest.getTypeOfRequest().isEmpty()
						&& updateOrderRequest.getTypeOfRequest().equalsIgnoreCase("update")) {
					updateCustomerFields(customerOrder, updateOrderRequest, false);
					customerOrder.setOrderStatus(OrderStatus.valueOf("COMPLETE"));
				} else if (updateOrderRequest.getTypeOfRequest() != null
						&& !updateOrderRequest.getTypeOfRequest().isEmpty()
						&& updateOrderRequest.getTypeOfRequest().equalsIgnoreCase("submited")) {
					if (updateCustomerFields(customerOrder, updateOrderRequest, true)) {

						ItemResponse historyResponse = submitOrderHelper.submitOrder(customerOrder);
						try {
							String nextOrderNumber=datebaseService.updateNextOrderNo(customerOrder.getCustomerId(), true).toString().concat(customerOrder.getCustomerId());
						
							updateProduct(customerOrder);
							customerOrderRepository.delete(customerOrder);
							BazarbalaUtil.isCookieAvailable(request, response, nextOrderNumber);
						} catch (Exception e) {
							// handle error
						}
						return historyResponse;
					}
				}

				BazarbalaUtil.processCustomerOrder(customerOrder);
				customerOrderRepository.save(customerOrder);
			}
		} else {
			customerOrder = new CustomerOrder();
		}
		itemResponse.setCustomerOrder(customerOrder);
		return itemResponse;

	}
	
	private boolean updateCustomerFields(CustomerOrder customerOrder, UpdateOrderRequest updateOrderRequest,
			boolean isSubmitted) {
		boolean isValidated = true;
		if (updateOrderRequest.getDeliveryMethod() != null && !updateOrderRequest.getDeliveryMethod().isEmpty()) {
			customerOrder.setDeliveryMethod(DeliveryMethod.valueOf(updateOrderRequest.getDeliveryMethod()));
		} else if (isSubmitted) {
			if (String.valueOf(customerOrder.getDeliveryMethod()) != null
					&& String.valueOf(customerOrder.getDeliveryMethod()).equalsIgnoreCase("NOTDEFINE")) {
				return false;
			}
		}
		if (updateOrderRequest.isOkaytoCall()) {
			customerOrder.setOkaytoCall(updateOrderRequest.isOkaytoCall());
		}
		if (updateOrderRequest.getPaymentMethod() != null && !updateOrderRequest.getPaymentMethod().isEmpty()) {
			customerOrder.setPaymentMethod(PaymentMethod.valueOf(updateOrderRequest.getPaymentMethod()));
		} else if (isSubmitted) {
			if (String.valueOf(customerOrder.getPaymentMethod()) != null
					&& String.valueOf(customerOrder.getPaymentMethod()).equalsIgnoreCase("NOTDEFINE")) {
				return false;
			}
		}
		if (updateOrderRequest.getPhoneNumber() != null && !updateOrderRequest.getPhoneNumber().isEmpty()) {
			customerOrder.setPhoneNumber(updateOrderRequest.getPhoneNumber());
		}
		if (updateOrderRequest.getZipCode() != null && !updateOrderRequest.getZipCode().isEmpty()) {
			customerOrder.setZipCode(updateOrderRequest.getZipCode());
		}
		
		if (updateOrderRequest.getShippingAddress()!= null && !updateOrderRequest.getShippingAddress().isEmpty()) {
			customerOrder.setShippingAddress(updateOrderRequest.getShippingAddress());
		}
		return isValidated;
	}
	
	/**
	 * 
	 * @param itemRequest
	 * @param request
	 * @param response
	 * @param itemResponse
	 * @return
	 */
	public ItemResponse createOrder(ItemRequest itemRequest, HttpServletRequest request, HttpServletResponse response,
			ItemResponse itemResponse) {

		String bin = BazarbalaUtil.genrateBalaJiBIN(request, response);
		CustomerOrder customerOrder = null;
		if (bin != null) {
			Optional<CustomerOrder> customerOptionalOrder = customerOrderRepository.findById(bin);

			if (customerOptionalOrder.isPresent()) {
				customerOrder = customerOptionalOrder.get();
			} else {
				customerOrder = new CustomerOrder();
				customerOrder.setOrderId(bin);
				customerOrder.setOrderDate(BazarbalaUtil.getCurrentDate().toString());
			}
			customerOrder.setLastUpdatedDate(BazarbalaUtil.getCurrentDate().toString());
			HttpSession session = request.getSession(true);
			
			PersonalInfo personalInfo=(PersonalInfo) session.getAttribute("PersonaInfo");
			if(personalInfo != null && personalInfo.getPersonalId() != null) {
				customerOrder.setCustomerId(personalInfo.getPersonalId());
			}
			itemResponse.setCustomerOrder(customerOrder);
			createOrUpdateOrderItems.createOrUpdateOrderItem(customerOrder, itemRequest, itemResponse);
          
			BazarbalaUtil.processCustomerOrder(customerOrder);
			customerOrder.setOrderStatus(OrderStatus.valueOf("INCOMPLETE"));
			customerOrderRepository.save(customerOrder);


		} else {
			customerOrder = new CustomerOrder();
		}
		itemResponse.setCustomerOrder(customerOrder);
		return itemResponse;

	}
	
	
	/**
	 * 
	 * @param itemRequest
	 * @param request
	 * @param response
	 * @param itemResponse
	 * @return
	 */
	public ItemResponse getOrder(HttpServletRequest request, HttpServletResponse response, ItemResponse itemResponse) {

		String bin = BazarbalaUtil.genrateBalaJiBIN(request, response);
		CustomerOrder customerOrder = null;
		if (bin != null) {
			Optional<CustomerOrder> customerOptionalOrder = customerOrderRepository.findById(bin);

			if (customerOptionalOrder.isPresent()) {
				customerOrder = customerOptionalOrder.get();
			}
			if (customerOrder != null) {
				itemResponse.setCustomerOrder(customerOrder);
				BazarbalaUtil.processCustomerOrder(customerOrder);

			} else {
				customerOrder = new CustomerOrder();
			}
		} else {
			customerOrder = new CustomerOrder();
		}
		HttpSession session = request.getSession(true);
		
		PersonalInfo personalInfo=(PersonalInfo) session.getAttribute("PersonaInfo");
		if(personalInfo != null && personalInfo.getPersonalId() != null && customerOrder != null) {
			customerOrder.setCustomerId(personalInfo.getPersonalId());
		}
		itemResponse.setCustomerOrder(customerOrder);
		return itemResponse;

	}
	
	private void updateProduct(CustomerOrder customerOrder) {

		for (OrderItem item : customerOrder.getOrderItems()) {
			Optional<ProductDetail> productOptinalDetail = productDetailRepository.findById(item.getProductCode());
			if (productOptinalDetail.isPresent()) {

				ProductDetail productDetail = productOptinalDetail.get();

				productDetail.setQuantity(productDetail.getQuantity() - item.getQuantity());
				

			}
		}

	}
	
	

}
