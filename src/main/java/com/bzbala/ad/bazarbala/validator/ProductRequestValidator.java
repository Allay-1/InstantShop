package com.bzbala.ad.bazarbala.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.product.model.ProductClientRequest;

@Component
public class ProductRequestValidator {

	public Result validateProductRequest(ProductClientRequest productRequest,String supplierId) {

		Result message = null;

		// Validating Mandatory parameter
		String productCode = productRequest.getProductCode() != null && !productRequest.getProductCode().isEmpty()
				? productRequest.getProductCode().trim()
				: null;

		if (productCode == null) {
			message = new Result(HttpStatus.BAD_REQUEST, false);
			message.setMessage("Product Request Mandatory paramerter Product code is missing::");
			return message;
		}

//		String supplierId = productRequest.getSupplierId() != null && !productRequest.getSupplierId().isEmpty()
//				? productRequest.getSupplierId().trim()
//				: null;
//
//		if (supplierId == null) {
//			message = new Result(HttpStatus.BAD_REQUEST, false);
//			message.setMessage("Product Request Mandatory paramerter Supplier id is missing::");
//			return message;
//		}
		productRequest.setProductCode(productCode + "_" + supplierId);

		List<String> errorMessages = new ArrayList<>();

		String productName = productRequest.getName() != null && !productRequest.getName().isEmpty()
				? productRequest.getName().trim()
				: null;

		if (productName == null) {
			errorMessages.add("Missing Product Name");
		} else {
			productRequest.setName(productName);
		}

		String category_code = productRequest.getCategoryCode() != null && !productRequest.getCategoryCode().isEmpty()
				? productRequest.getCategoryCode().trim()
				: null;

		if (category_code == null) {
			errorMessages.add("Missing Category Code");
		} else {
			productRequest.setCategoryCode(category_code + "_" + supplierId);
		}

		Integer quantity = productRequest.getQuantity() != null && productRequest.getQuantity() > 0
				? productRequest.getQuantity()
				: null;

		if (quantity == null) {
			errorMessages.add("Missing Quantity for product");
		} else {
			productRequest.setQuantity(quantity);
		}

		Double basePrice = productRequest.getBasePrice() != null && productRequest.getBasePrice() > 0.0
				? productRequest.getBasePrice()
				: 0.0;

		if (basePrice == 0.0) {
			errorMessages.add("Base Price either missing or less than 0.0");
		} else {
			productRequest.setBasePrice(basePrice);
		}
		message = errorResponse(productCode, errorMessages);
		if (!message.isValid()) {
			return message;
		}
		// End
		// Setting default value if it is not present like

		productRequest
				.setCategoryName(productRequest.getCategoryName() != null && !productRequest.getCategoryName().isEmpty()
						? productRequest.getCategoryName().trim()
						: "All");
		productRequest
				.setQuatityType(productRequest.getQuatityType() != null && !productRequest.getQuatityType().isEmpty()
						? productRequest.getQuatityType().trim()
						: "KG");

		productRequest.setOrgin(productRequest.getOrgin() != null && !productRequest.getOrgin().isEmpty()
				? productRequest.getOrgin().trim()
				: "INDIA");

		productRequest.setDiscount(productRequest.getDiscount() != null && !productRequest.getDiscount().isEmpty()
				? productRequest.getDiscount().trim()
				: "O");

		productRequest
				.setDiscountType(productRequest.getDiscountType() != null && !productRequest.getDiscountType().isEmpty()
						? productRequest.getDiscountType().trim()
						: "NOTDEFINED");
		productRequest
				.setCurrencyType(productRequest.getCurrencyType() != null && !productRequest.getCurrencyType().isEmpty()
						? productRequest.getCurrencyType().trim()
						: "RS");
        if(Integer.valueOf(productRequest.getDiscount())>0 && productRequest.getDiscountType().equalsIgnoreCase("NOTDEFINED")) {
        	productRequest.setDiscountType("PERCENTAGE");
        }
		// End
		message = new Result(HttpStatus.OK, true);
		return message;
	}

	public Result errorResponse(String productCode, List<String> errorMessages) {
		Result message = null;
		StringBuilder stringBuilder = new StringBuilder();
		boolean isError = false;
		for (String errorMessage : errorMessages) {
			stringBuilder.append(errorMessage);
			stringBuilder.append(",");
			isError = true;
		}
		if (isError) {

			message = new Result(HttpStatus.BAD_REQUEST, false);
			message.setMessage(stringBuilder.toString() + productCode);
		} else {
			message = new Result(HttpStatus.OK, true);
		}

		return message;

	}

}
