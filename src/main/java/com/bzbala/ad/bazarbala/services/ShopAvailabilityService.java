package com.bzbala.ad.bazarbala.services;

import java.util.List;
import java.util.function.Function;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.bzbala.ad.bazarbala.dao.DBServices;
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.model.ShopAvailability;
import com.bzbala.ad.bazarbala.model.UserReposistory;

@Component
public class ShopAvailabilityService {
	
	@Autowired
	DBServices userService;
	
	@Value("${domain.path}")
	private String contextPath;
	
	public List<ShopAvailability>  getShopAvailability(String zipCode,String shopCategory,HttpServletRequest request) throws BazarBalaDAOException{
		
		List<UserReposistory> userReposistory=userService.getSupplierrDetail(zipCode);
		List<ShopAvailability> shops = new ArrayList<>();
		String shopBaseUrl=getURLValue(request);
		if(userReposistory !=null && !userReposistory.isEmpty()) {
			
			shops=userReposistory.stream().map((Function<? super UserReposistory, ? extends ShopAvailability>) user ->{
				ShopAvailability shop = new ShopAvailability();
				if(user.getShopAddress()!=null)
				shop.setShopAddress(user.getShopAddress());
				if(user.getShopName()!=null)
				shop.setShopName(user.getShopName());
				if(user.getShopType()!=null)
				shop.setShopType(user.getShopType().toString());
				shop.setShopUrl(shopBaseUrl+user.getShopName()+"/"+user.getShopId());
				if(user.getZipCode()!=null)
				shop.setShopZip(user.getZipCode().toString());
				return shop;
			}).sorted((u1,u2)-> u1.getShopZip().compareTo(u2.getShopZip())).collect(Collectors.toList());
			
		}
		return shops;
		
	}
	
	public String getURLValue(HttpServletRequest request){
	    String hostUrl = contextPath+"supplier/";
	    return hostUrl;
	}
	

}
