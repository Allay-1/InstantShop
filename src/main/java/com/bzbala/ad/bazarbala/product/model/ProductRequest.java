package com.bzbala.ad.bazarbala.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRequest {
	
	private List<ProductClientRequest> productRequest;

	public List<ProductClientRequest> getProductRequest() {
		return productRequest;
	}

	public void setProductRequesst(List<ProductClientRequest> productRequest) {
		this.productRequest = productRequest;
	}

	@Override
	public String toString() {
		return "ProductRequest [productRequest=" + productRequest + "]";
	}
	
	

}
