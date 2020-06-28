package com.bzbala.ad.bazarbala.controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.bzbala.ad.bazarbala.dto.AuthUserUserDTO;
import com.bzbala.ad.bazarbala.dto.AuthenticationRequest;
import com.bzbala.ad.bazarbala.dto.AuthenticationResponse;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.services.AuthenticationService;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;
import com.bzbala.ad.bazarbala.util.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class AuthenticationController {
	
	
	@Autowired
	AuthenticationService authenticationServiceObj;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	
	   @RequestMapping(value = "/user/auth/v1/User", method = RequestMethod.POST)
	    public ResponseEntity<Boolean> authenticateUser(@RequestBody AuthUserUserDTO authUserUserDTO) {
	    	boolean isUpdated = false;
	    	isUpdated= authenticationServiceObj.authenticateUserSer(authUserUserDTO.getPhoneNo(),authUserUserDTO.getPwdToAuth());
	        if (!isUpdated) {
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(isUpdated, HttpStatus.OK);
	    }
	
	   
	   @RequestMapping(value = "/user/auth/v1/loadUser/{userName}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	   @ResponseBody
	    public UserDetails loadUser(@PathVariable String userName) {
		   
		   UserDetails userDetails = null;
		   
		   try {    	
			   userDetails = authenticationServiceObj.loadUserByUsername (userName);		   
		   } catch (BadCredentialsException e) {
				e.printStackTrace();
			}
		   
	
		 return userDetails;
		   
	   }
	   
	@RequestMapping(value = "/user/auth/v1/authAndCreateToken", method = RequestMethod.POST)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse response) throws Exception {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		Result message = null;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getPhoneNo(), BazarbalaUtil.generatePwdEnc(authenticationRequest.getPassword())));
		} catch (BadCredentialsException e) {
			message = new Result(HttpStatus.OK, e, false);
			builder.status(Response.Status.BAD_REQUEST).entity(message);
			return builder.build();
		}

		final UserDetails userDetails = authenticationServiceObj.loadUserByUsername(authenticationRequest.getPhoneNo());

		final String jwt = "Bearer " + jwtTokenUtil.generateToken(userDetails);
		
		message = new Result(HttpStatus.OK, true);
		message.setMessage("Success: ");
		response.addHeader("Authorization", jwt);
		builder.status(Response.Status.OK).entity(message);
		return builder.build();
	}
	
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
					authenticationRequest.getPhoneNo(), BazarbalaUtil.generatePwdEnc(authenticationRequest.getPassword())));
		} catch (BadCredentialsException e) {
			message = new Result(HttpStatus.OK, e, false);
			
			return message;
		}

		final UserDetails userDetails = authenticationServiceObj.loadUserByUsername(authenticationRequest.getPhoneNo());

		final String jwt = "Bearer " + jwtTokenUtil.generateToken(userDetails);
		
		message = new Result(HttpStatus.OK, true);
		message.setMessage("Success: ");
		response.addHeader("Authorization", jwt);
		return message;
	}
}
