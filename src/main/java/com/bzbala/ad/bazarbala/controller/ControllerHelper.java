package com.bzbala.ad.bazarbala.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.bzbala.ad.bazarbala.dto.AuthenticationRequest;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.services.AuthenticationService;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;
import com.bzbala.ad.bazarbala.util.JwtUtil;

@Component
public class ControllerHelper {
	
	@Autowired
	AuthenticationService authenticationServiceObj;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	
	/**
	 * 
	 * @param authenticationRequest
	 * @param response
	 * @return
	 */
	public Result createToken(AuthenticationRequest authenticationRequest,HttpServletResponse response) {
		Result message = null;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getPhoneNo()+"_"+authenticationRequest.getUserType(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			message = new Result(HttpStatus.OK, e, false);
			message.setMessage("Token creation has failed due to technical issue but you now register customer");
			return message;
		}

		final UserDetails userDetails = authenticationServiceObj.loadUserByUsername(authenticationRequest.getPhoneNo(),authenticationRequest.getUserType());
		final String jwt = "Bearer " + jwtTokenUtil.generateToken(userDetails);
		response.addHeader("Authorization", jwt);
		response.addHeader("User_Type", authenticationRequest.getUserType());
		return message;
	}

}
