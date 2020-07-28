package com.bzbala.ad.bazarbala.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bzbala.ad.bazarbala.dto.AuthenticationRequest;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.services.AuthenticationService;

@Component
public class ControllerHelper {
	
	@Autowired
	AuthenticationService authenticationServiceObj;
	
	@Autowired
	AuthenticationController authenticationController;
	
	
	
	/**
	 * 
	 * @param authenticationRequest
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public Result createToken(AuthenticationRequest authenticationRequest,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result message = null;
		
		authenticationController.createAuthenticationToken(authenticationRequest,request,response);
		
		return message;
	}

}
