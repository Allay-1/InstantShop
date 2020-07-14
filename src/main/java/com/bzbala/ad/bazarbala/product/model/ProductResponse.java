package com.bzbala.ad.bazarbala.product.model;

import java.util.Map;

public class ProductResponse {
	
	private Map<String,String> productImportSuccess;
	
	private Map<String ,String > productImportFail;

	public Map<String, String> getProductImportSuccess() {
		return productImportSuccess;
	}

	public void setProductImportSuccess(Map<String, String> productImportSuccess) {
		this.productImportSuccess = productImportSuccess;
	}

	public Map<String, String> getProductImportFail() {
		return productImportFail;
	}

	public void setProductImportFail(Map<String, String> productImportFail) {
		this.productImportFail = productImportFail;
	}
	
	
}
