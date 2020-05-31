package com.bzbala.ad.bazarbala.dto;

public class User {

    private String name;
    private String occupation;
    private String zipCode;

    public String getName() {
       return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}