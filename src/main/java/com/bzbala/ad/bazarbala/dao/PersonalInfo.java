package com.bzbala.ad.bazarbala.dao;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

public class PersonalInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserDetails userDetail;
	
	private String phoneNo;
	
	private String personalId;
	
    private String id;
    
    private String address;
    
    private Integer nextOrderId;
    
    private String userType;

	public UserDetails getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetails userDetail) {
		this.userDetail = userDetail;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
