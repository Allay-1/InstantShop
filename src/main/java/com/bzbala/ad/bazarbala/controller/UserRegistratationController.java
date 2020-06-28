package com.bzbala.ad.bazarbala.controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bzbala.ad.bazarbala.dto.AuthenticationRequest;
import com.bzbala.ad.bazarbala.dto.InstantShopCustomer;
import com.bzbala.ad.bazarbala.dto.InstantShopSupplier;
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.services.AdminServices;
import com.bzbala.ad.bazarbala.validator.RequestValidator;


@Controller
public class UserRegistratationController {

	@Autowired
	AdminServices adminServices;
	
	@Autowired
	RequestValidator requestValidator;
	
	@Autowired
	ControllerHelper controllerHelper;
	
	@GetMapping("ux/login")
	public String loginUser()
	{
		return "loginBoth";
	}

	@GetMapping("/user/customerlogin")
	public String getCustomerUser(InstantShopSupplier createBusinessUserDTO) {
		return "shopCustomerSignup";
	}
	
	@GetMapping("/user/suplierlogin")
	public String getSuplierUser(InstantShopSupplier createBusinessUserDTO) {
		return "shopSuplierSignup";
	}
    /**
     * Create the Supplier Account who server the orders
     * @param createBusinessUserDTO
     * @return
     */
	@PostMapping(value = "/user/signUpSupplier")
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response createSuplier(@RequestBody InstantShopSupplier createBusinessUserDTO,
			HttpServletResponse response) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		Result message = null;
		message = requestValidator.validateSupplierRequest(createBusinessUserDTO);
		if (!message.isValid()) {
			return builder.status(Response.Status.BAD_REQUEST).entity(message).build();
		}
		message = null;
		try {
			message = adminServices.creteSupplier(createBusinessUserDTO);
		} catch (BazarBalaDAOException bazarBalaDAOException) {
			message = new Result(HttpStatus.OK, bazarBalaDAOException, false);
		}
		if (message.isValid()) {
			AuthenticationRequest authenticationRequest = new AuthenticationRequest();
			authenticationRequest.setPassword(createBusinessUserDTO.getSupplierPassword());
			authenticationRequest.setPhoneNo(createBusinessUserDTO.getPhoneNnumber());
			controllerHelper.createToken(authenticationRequest, response);
			builder.status(Response.Status.OK).entity(message);
			return builder.build();
		} else {

			builder.status(Response.Status.BAD_REQUEST).entity(message);
			return builder.build();
		}

	}
	
	/**
	 * Creating the Customer end point, when customer comes  first time 
	 * Need to create a account
	 * @param createBusinessUserDTO
	 * @return
	 */
	@PostMapping(value = "/user/signUPCustomer")
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response createCustomer(@RequestBody InstantShopCustomer createBusinessUserDTO,
			HttpServletResponse response) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		Result message = null;
		message = requestValidator.validateCustomerRequest(createBusinessUserDTO);
		if (!message.isValid()) {
			return builder.status(Response.Status.BAD_REQUEST).entity(message).build();
		}
		message = null;
		try {
			message = adminServices.creteCustomer(createBusinessUserDTO);
		} catch (BazarBalaDAOException bazarBalaDAOException) {
			message = new Result(HttpStatus.OK, bazarBalaDAOException, false);
		}
		if (message.isValid()) {
			AuthenticationRequest authenticationRequest = new AuthenticationRequest();
			authenticationRequest.setPassword(createBusinessUserDTO.getCustomerPwd());
			authenticationRequest.setPhoneNo(createBusinessUserDTO.getPhoneNnumber());
			controllerHelper.createToken(authenticationRequest, response);

			builder.status(Response.Status.OK).entity(message);
			return builder.build();
		} else {
			builder.status(Response.Status.BAD_REQUEST).entity(message);
			return builder.build();
		}

	}

}
