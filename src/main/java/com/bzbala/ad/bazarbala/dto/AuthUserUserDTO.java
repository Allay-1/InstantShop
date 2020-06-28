package com.bzbala.ad.bazarbala.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUserUserDTO {
	
	
	private String pwdToAuth;
	private String phoneNo;
	private String userType;
	
	
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
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}