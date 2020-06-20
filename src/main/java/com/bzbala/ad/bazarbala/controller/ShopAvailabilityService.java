package com.bzbala.ad.bazarbala.controller;

import java.util.List;
import java.util.function.Function;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import com.bzbala.ad.bazarbala.dao.DBServices;
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.model.ShopAvailability;
import com.bzbala.ad.bazarbala.model.UserReposistory;

@Component
public class ShopAvailabilityService {
	
	@Autowired
	DBServices userService;
	
	public List<ShopAvailability>  getShopAvailability(String zipCode,String shopCategory) throws BazarBalaDAOException{
		
		List<UserReposistory> userReposistory=userService.getBusinessUser(zipCode);
		List<ShopAvailability> shops = new ArrayList<>();
		if(userReposistory !=null && !userReposistory.isEmpty()) {
			
			shops=userReposistory.stream().map((Function<? super UserReposistory, ? extends ShopAvailability>) user ->{
				ShopAvailability shop = new ShopAvailability();
				if(user.getShopAddress()!=null)
				shop.setShopAddress(user.getShopAddress());
				if(user.getShopName()!=null)
				shop.setShopName(user.getShopName());
				if(user.getShopType()!=null)
				shop.setShopType(user.getShopType().toString());
				shop.setShopUrl(user.getShopUrl());
				if(user.getZipCode()!=null)
				shop.setShopZip(user.getZipCode().toString());
				return shop;
			}).sorted((u1,u2)-> u1.getShopZip().compareTo(u2.getShopZip())).collect(Collectors.toList());
			
		}
		return shops;
		
	}
	

}
