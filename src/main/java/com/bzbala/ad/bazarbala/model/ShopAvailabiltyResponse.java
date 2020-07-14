package com.bzbala.ad.bazarbala.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopAvailabiltyResponse {
	
	List<ShopAvailability> shops;

	public List<ShopAvailability> getShops() {
		return shops;
	}

	public void setShops(List<ShopAvailability> shops) {
		this.shops = shops;
	}

	@Override
	public String toString() {
		return "ShopAvailabiltyResponse [shops=" + shops + "]";
	}
	
	

}
