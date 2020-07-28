package com.bzbala.ad.bazarbala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUserUserDTO {
	
	
	private String pwdToAuth;
	private String phoneNo;
	private String personalId;
    private String id;
    private String address;
    private Integer nextOrderId;
    private String userType;
	
    
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwdToAuth() {
		return pwdToAuth;
	}
	public void setPwdToAuth(String pwdToAuth) {
		this.pwdToAuth = pwdToAuth;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getNextOrderId() {
		return nextOrderId;
	}
	public void setNextOrderId(Integer nextOrderId) {
		this.nextOrderId = nextOrderId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}