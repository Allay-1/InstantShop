package com.bzbala.ad.bazarbala.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.model.ShopAvailability;
import com.bzbala.ad.bazarbala.model.ShopAvailabiltyResponse;
import com.bzbala.ad.bazarbala.services.ShopAvailabilityService;




@Controller
public class ShopAvailabilityController {
	
	@Autowired
	ShopAvailabilityService shopAvailabilityService;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/supplier/availability", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ShopAvailabiltyResponse getShops(@RequestParam String zipCode, @RequestParam String shopType,HttpServletRequest request) throws BazarBalaDAOException {
		ShopAvailabiltyResponse shopAvailabiltyResponse = new ShopAvailabiltyResponse();
		List<ShopAvailability> shops = shopAvailabilityService.getShopAvailability(zipCode, shopType,request);
		shopAvailabiltyResponse.setShops(shops);
		
		
		return shopAvailabiltyResponse;
	}

}
