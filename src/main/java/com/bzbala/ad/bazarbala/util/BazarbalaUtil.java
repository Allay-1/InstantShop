package com.bzbala.ad.bazarbala.util;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bzbala.ad.bazarbala.order.model.CustomerOrder;
import com.bzbala.ad.bazarbala.order.model.Item;
import com.bzbala.ad.bazarbala.order.model.ItemRequest;
import com.bzbala.ad.bazarbala.order.model.OrderHistory;
import com.bzbala.ad.bazarbala.order.model.OrderHistoryItem;
import com.bzbala.ad.bazarbala.order.model.OrderItem;
import com.bzbala.ad.bazarbala.product.model.OrderStatus;
import com.bzbala.ad.bazarbala.product.model.ProductDetail;

public final class BazarbalaUtil {

	private static final String dateFormate = "dd-MM-yyyy";

	/**
	 * 
	 * @param len
	 * @return
	 */
	public static final long generateRandomID(int len) {

		if (len > 18)
			throw new IllegalStateException("To many digits");
		long tLen = (long) Math.pow(10, len - 1) * 9;

		return (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;
	}

	/**
	 * 
	 * @param pwdStr
	 * @return
	 */
	public static final String generatePwdEnc(String pwdStr) {
		return pwdStr + "@123";
	}

	/**
	 * 
	 * @param phoneNo
	 * @return
	 */
	public static boolean validatePhoneNumber(String phoneNo) {
		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}"))
			return true;
		// validating phone number with -, . or spaces
		else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
			return true;
		// validating phone number with extension length from 3 to 5
		else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
			return true;
		// validating phone number where area code is in braces ()
		else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
			return true;
		// return false if nothing matches the input
		else
			return false;

	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public static String genrateBalaJiBIN(HttpServletRequest request, HttpServletResponse response) {
		boolean isCookieAvailable = false;
		String cokieName = "BIN";
		String generatedString = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cokieName.equals(cookie.getName())) {
					if (cookie.getValue() != null && !cookie.getValue().isEmpty()) {
						isCookieAvailable = true;
						generatedString = cookie.getValue();
						break;
					}
				}
			}
		}
		if (!isCookieAvailable) {

			// request.getSession().setAttribute("MY_SESSION_MESSAGES", generatedString);
			generatedString=String.valueOf(generateRandomID(16));
			Cookie binCookie = new Cookie("BIN", generatedString);
			binCookie.setMaxAge(30 * 60);
			binCookie.setDomain("bazarwala.com");
			binCookie.setPath("");
			response.addCookie(binCookie);

		}

		return generatedString;
	}
	
	public static void isCookieAvailable(HttpServletRequest request, HttpServletResponse response,String customerBin) {
		boolean isCookieAvailable = false;
		String cokieName = "BIN";
		String generatedString = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cokieName.equals(cookie.getName())) {
					if (cookie.getValue() != null && !cookie.getValue().isEmpty()) {
						generatedString = cookie.getValue();
						if(customerBin.equalsIgnoreCase(generatedString)) {
							
							cookie.setValue(customerBin);  
							 cookie.setMaxAge(60*60*24*3);   
							 cookie.setPath("/");   
							 response.addCookie(cookie);
							isCookieAvailable = true;
						}
					}
				}
			}
		}
		if (!isCookieAvailable) {
			Cookie binCookie = new Cookie("BIN", customerBin);
			binCookie.setMaxAge(60*60*24*3);   
			binCookie.setPath("/");   
			 response.addCookie(binCookie);
			response.addCookie(binCookie);
           
		}
	}
	
	
	public static void removeCookie(HttpServletResponse response,String cookieName,String cookieValue) {
		Cookie userNameCookieRemove = new Cookie(cookieName, "");
		userNameCookieRemove.setMaxAge(0);
		response.addCookie(userNameCookieRemove);
	}
	
	public static String getBalaJiBIN(HttpServletRequest request, HttpServletResponse response) {
		String cokieName = "BIN";
		String generatedString = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cokieName.equals(cookie.getName())) {
					if (cookie.getValue() != null && !cookie.getValue().isEmpty()) {
						generatedString = cookie.getValue();
						break;
					}
				}
			}
		}
		return generatedString;
	}

	public static List<ProductDetail> getProductList(List<ProductDetail> products) {

		products = products.stream().map(product -> {
			int indexOfUnderScore = product.getProductCode().lastIndexOf("_");
			product.setProductCode(product.getProductCode().substring(0, indexOfUnderScore));
			return product;
		}).collect(Collectors.toList());

		return products;

	}

	public static List<OrderItem> getOrderList(List<OrderItem> orderItem) {

		orderItem = orderItem.stream().map(product -> {
			
			return product;
		}).collect(Collectors.toList());

		return orderItem;

	}

	public static String getProductCode(String productId) {

		int indexOfUnderScore = productId.lastIndexOf("_");
		productId = productId.substring(0, indexOfUnderScore);
		return productId;

	}
	
	public static String getSupplierCode(String productId) {

		int indexOfUnderScore = productId.lastIndexOf("_");
		productId = productId.substring(indexOfUnderScore+1, productId.length());
		return productId;

	}
	
	

	public static List<Item> getItemProductList(List<Item> items, String supplierId) {

		items = items.stream().map(item -> {

			item.setProductCode(item.getProductCode() + "_" + supplierId);
			return item;
		}).collect(Collectors.toList());

		return items;

	}

	public static List<OrderItem> getOrderItem(List<OrderItem> orderItems, String supplierId) {

		orderItems = orderItems.stream().map(item -> {

			item.setProductCode(item.getProductCode() + "_" + supplierId);
			return item;
		}).collect(Collectors.toList());

		return orderItems;

	}

	public static Item getOrderItem(Item item, String supplierId) {
		item.setProductCode(item.getProductCode() + "_" + supplierId);

		return item;

	}

	public static Date convertStringToDate(String inputDateString) throws ParseException {

		return new SimpleDateFormat(dateFormate).parse(inputDateString);

	}

	public static boolean compareDates(Date startDate, Date endDate) {
		return new SimpleDateFormat(dateFormate).format(startDate)
				.compareTo(new SimpleDateFormat(dateFormate).format(startDate)) >= 0 ? true : false;
	}

	public static Date getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormate);
		Date date = new Date();
		formatter.format(date);
		return date;
	}
	
	/**
	 * 
	 * @param customerOrder
	 * @return
	 */
	public static CustomerOrder processCustomerOrder(CustomerOrder customerOrder) {
		Double totalAmount = 0.0;
		double tax=0.0;
		if(customerOrder.getTaxPercentange() !=null) {
		   tax = customerOrder.getTaxPercentange();
		}
		double amount = 0.0;
		for (OrderItem item : customerOrder.getOrderItems()) {
			totalAmount += item.getTotalPrice();
		}
		if (tax > 0 && totalAmount > 0) {

			amount = (tax / 100) * totalAmount;
		}
		customerOrder.setOrderSubTotal(totalAmount + amount);
		return customerOrder;
	}

	public static OrderHistory processHistoryCustomerOrder(OrderHistory orderHistory) {
		Double totalAmount = 0.0;
		double tax=0.0;
		if(orderHistory.getTaxPercentange() !=null) {
		   tax = orderHistory.getTaxPercentange();
		}
		double amount = 0.0;
		for (OrderHistoryItem item : orderHistory.getOrderItems()) {
			totalAmount += item.getTotalPrice();
		}
		if (tax > 0 && totalAmount > 0) {

			amount = (tax / 100) * totalAmount;
		}
		orderHistory.setOrderSubTotal(totalAmount + amount);
		return orderHistory;
	}
	
	
	/**
	 * 
	 * @param customerOrder
	 * @return
	 */
	public static ItemRequest createItemRequest(CustomerOrder customerOrder) {
		ItemRequest itemRequest = new ItemRequest();
		List<Item> items=null;
		if(customerOrder.getOrderItems() != null && !customerOrder.getOrderItems().isEmpty()) {
			items=customerOrder.getOrderItems().stream().map(orderItem->{
				Item item = new Item();
				item.setProductCode(orderItem.getProductCode());
				item.setQuantity(orderItem.getQuantity());
				item.setActionType("add");
				return item;
			}).collect(Collectors.toList());
		}
		itemRequest.setItems(items);
		return itemRequest;
	}

}
