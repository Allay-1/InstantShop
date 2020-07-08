package com.bzbala.ad.bazarbala.dto;

public class CreateTokenRequest {
	
	private String phoneNumber;
	
	private String emailId;
	
	private String userType;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "CreateTokenRequest [phoneNumber=" + phoneNumber + ", emailId=" + emailId + ", userType=" + userType
				+ "]";
	}
	
	

}
