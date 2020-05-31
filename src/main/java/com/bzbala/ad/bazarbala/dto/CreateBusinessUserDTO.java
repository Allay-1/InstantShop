package com.bzbala.ad.bazarbala.dto;

public class CreateBusinessUserDTO {
	
	
	
	
	private Long customerId;  //SHOP_ID
	private Long shopId;
	private String shopName; //SHOP_NAME
	private String lastOwnerName;
	private String shopAddress; //SHOP_ADDRESS
	private String zipCode; //ZIPCODE
	private String phoneNnumber; //PHONE_NO
	private String shopOwnerEmailId; //SHOP_OWNER_EMAIL_ID
	private String shopOwnerPwd;  //SHOP_OWNER_PWD
	private String paymentPreference;
	
	
	
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastOwnerName == null) ? 0 : lastOwnerName.hashCode());
		result = prime * result + ((phoneNnumber == null) ? 0 : phoneNnumber.hashCode());
		result = prime * result + ((shopName == null) ? 0 : shopName.hashCode());
		result = prime * result + ((shopOwnerEmailId == null) ? 0 : shopOwnerEmailId.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateBusinessUserDTO other = (CreateBusinessUserDTO) obj;
		if (lastOwnerName == null) {
			if (other.lastOwnerName != null)
				return false;
		} else if (!lastOwnerName.equals(other.lastOwnerName))
			return false;
		if (phoneNnumber == null) {
			if (other.phoneNnumber != null)
				return false;
		} else if (!phoneNnumber.equals(other.phoneNnumber))
			return false;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		if (shopOwnerEmailId == null) {
			if (other.shopOwnerEmailId != null)
				return false;
		} else if (!shopOwnerEmailId.equals(other.shopOwnerEmailId))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return "CreateBusinessUserDTO [customerId=" + customerId + ", shopId=" + shopId + ", shopName=" + shopName
				+ ", lastOwnerName=" + lastOwnerName + ", shopAddress=" + shopAddress + ", zipCode=" + zipCode
				+ ", phoneNnumber=" + phoneNnumber + ", shopOwnerEmailId=" + shopOwnerEmailId + ", shopOwnerPwd="
				+ shopOwnerPwd + ", paymentPreference=" + paymentPreference + "]";
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getLastOwnerName() {
		return lastOwnerName;
	}
	public void setLastOwnerName(String lastOwnerName) {
		this.lastOwnerName = lastOwnerName;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhoneNnumber() {
		return phoneNnumber;
	}
	public void setPhoneNnumber(String phoneNnumber) {
		this.phoneNnumber = phoneNnumber;
	}
	public String getShopOwnerEmailId() {
		return shopOwnerEmailId;
	}
	public void setShopOwnerEmailId(String shopOwnerEmailId) {
		this.shopOwnerEmailId = shopOwnerEmailId;
	}
	public String getShopOwnerPwd() {
		return shopOwnerPwd;
	}
	public void setShopOwnerPwd(String shopOwnerPwd) {
		this.shopOwnerPwd = shopOwnerPwd;
	}
	public String getPaymentPreference() {
		return paymentPreference;
	}
	public void setPaymentPreference(String paymentPreference) {
		this.paymentPreference = paymentPreference;
	}
	
}