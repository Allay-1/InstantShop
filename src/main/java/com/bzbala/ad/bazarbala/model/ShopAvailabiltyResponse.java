package com.bzbala.ad.bazarbala.model;

import java.util.List;

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
