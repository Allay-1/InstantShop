package com.bzbala.ad.bazarbala.product.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProductDetailResponse {
	
	List<ProductDetail> productDetails;

	public List<ProductDetail> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetail> productDetails) {
		this.productDetails = productDetails;
	}

	@Override
	public String toString() {
		return "ProductDetailResponse [productDetails=" + productDetails + "]";
	}
	
	

}
