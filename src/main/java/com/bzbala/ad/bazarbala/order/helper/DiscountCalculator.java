package com.bzbala.ad.bazarbala.order.helper;
import org.springframework.stereotype.Component;

import com.bzbala.ad.bazarbala.order.model.OrderHistoryItem;
import com.bzbala.ad.bazarbala.order.model.OrderItem;
import com.bzbala.ad.bazarbala.product.model.Discount;
import com.bzbala.ad.bazarbala.product.model.Discount_Type;
import com.bzbala.ad.bazarbala.product.model.Price;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;
@Component
public class DiscountCalculator {
	
	/**
	 * 
	 * @param orderItem
	 * @param discount
	 * @param price
	 * @return
	 * @throws Exception
	 */
	public OrderItem calcualteDiscount(OrderItem orderItem, Discount discount, Price price) throws Exception {

		String discountValue = discount.getDiscount();
		discount.getTypeOfDiscount();
		if (discountValue != null && Integer.valueOf(discountValue) > 0
				&& BazarbalaUtil.compareDates(BazarbalaUtil.getCurrentDate(),
						BazarbalaUtil.convertStringToDate(discount.getDiscountStartDate()))
				&& BazarbalaUtil.compareDates(BazarbalaUtil.convertStringToDate(discount.getDiscountStartDate()),
						BazarbalaUtil.convertStringToDate(discount.getDiscountEndDate()))) {
			if (String.valueOf(discount.getTypeOfDiscount()).equalsIgnoreCase("PERCENTAGE")) {
				orderItem.setDiscountType(Discount_Type.PERCENTAGE);
				orderItem.setDiscount(Integer.valueOf(discountValue));
				orderItem.setTotalPrice(orderItem.getTotalPrice()
						- calculatePercentage(Double.valueOf(discountValue), orderItem.getTotalPrice()));
			} else if (String.valueOf(discount.getTypeOfDiscount()).equalsIgnoreCase("FLATAMOUNT")) {
				orderItem.setDiscountType(Discount_Type.FLATAMOUNT);
				orderItem.setDiscount(Integer.valueOf(discountValue));
				orderItem.setTotalPrice(orderItem.getTotalPrice() - Double.valueOf(discountValue));
			} else if (String.valueOf(discount.getTypeOfDiscount()).equalsIgnoreCase("QUANTITY")) {
				orderItem.setDiscountType(Discount_Type.QUANTITY);
				orderItem.setDiscount(Integer.valueOf(discountValue));
				orderItem.setQuantity(orderItem.getQuantity() + Integer.valueOf(discountValue));
			} else {
				orderItem.setDiscountType(Discount_Type.NOTDEFINED);
			}

		} else {
			orderItem.setDiscountType(Discount_Type.NOTDEFINED);
		}

		return orderItem;
	}
	
	public OrderHistoryItem calcualteHistoryDiscount(OrderHistoryItem orderItem, Discount discount, Price price) throws Exception {

		String discountValue = discount.getDiscount();
		discount.getTypeOfDiscount();
		if (discountValue != null && Integer.valueOf(discountValue) > 0
				&& BazarbalaUtil.compareDates(BazarbalaUtil.getCurrentDate(),
						BazarbalaUtil.convertStringToDate(discount.getDiscountStartDate()))
				&& BazarbalaUtil.compareDates(BazarbalaUtil.convertStringToDate(discount.getDiscountStartDate()),
						BazarbalaUtil.convertStringToDate(discount.getDiscountEndDate()))) {
			if (String.valueOf(discount.getTypeOfDiscount()).equalsIgnoreCase("PERCENTAGE")) {
				orderItem.setDiscountType(Discount_Type.PERCENTAGE);
				orderItem.setDiscount(Integer.valueOf(discountValue));
				orderItem.setTotalPrice(orderItem.getTotalPrice()
						- calculatePercentage(Double.valueOf(discountValue), orderItem.getTotalPrice()));
			} else if (String.valueOf(discount.getTypeOfDiscount()).equalsIgnoreCase("FLATAMOUNT")) {
				orderItem.setDiscountType(Discount_Type.FLATAMOUNT);
				orderItem.setDiscount(Integer.valueOf(discountValue));
				orderItem.setTotalPrice(orderItem.getTotalPrice() - Double.valueOf(discountValue));
			} else if (String.valueOf(discount.getTypeOfDiscount()).equalsIgnoreCase("QUANTITY")) {
				orderItem.setDiscountType(Discount_Type.QUANTITY);
				orderItem.setDiscount(Integer.valueOf(discountValue));
				orderItem.setQuantity(orderItem.getQuantity() + Integer.valueOf(discountValue));
			} else {
				orderItem.setDiscountType(Discount_Type.NOTDEFINED);
			}

		} else {
			orderItem.setDiscountType(Discount_Type.NOTDEFINED);
		}

		return orderItem;
	}
	
	/**
	 * 
	 * @param obtained
	 * @param total
	 * @return
	 */
	private double calculatePercentage(double obtained, double total) {
        return (obtained/100) * total;
    }
}
