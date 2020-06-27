package com.bzbala.ad.bazarbala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstantShopSupplier {

	private Long supplierId; // SHOP_ID
	private Long shopId;
	private Long addressId;
	private String shopName; // SHOP_NAME
	private String phoneNnumber; // PHONE_NO
	private String emailId; // SHOP_OWNER_EMAIL_ID
	private String supplierPassword; // SHOP_OWNER_PWD
	private String category;
	private String address; 
	private String zipCode;
	
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPhoneNnumber() {
		return phoneNnumber;
	}
	public void setPhoneNnumber(String phoneNnumber) {
		this.phoneNnumber = phoneNnumber;
	}
	
    
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	public String getSupplierPassword() {
		return supplierPassword;
	}
	public void setSupplierPassword(String supplierPassword) {
		this.supplierPassword = supplierPassword;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}