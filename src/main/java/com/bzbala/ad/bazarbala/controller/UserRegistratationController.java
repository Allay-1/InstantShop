package com.bzbala.ad.bazarbala.controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bzbala.ad.bazarbala.dto.AuthenticationRequest;
import com.bzbala.ad.bazarbala.dto.CreateTokenRequest;
import com.bzbala.ad.bazarbala.dto.ForgetPassword;
import com.bzbala.ad.bazarbala.dto.InstantShopCustomer;
import com.bzbala.ad.bazarbala.dto.InstantShopSupplier;
import com.bzbala.ad.bazarbala.dto.UpdatePassword;
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.mail.BazarbalaEmailImpl;
import com.bzbala.ad.bazarbala.services.AdminServices;
import com.bzbala.ad.bazarbala.validator.RequestValidator;

@CrossOrigin(maxAge = 3600)
@Controller
public class UserRegistratationController {

	@Autowired
	AdminServices adminServices;
	
	@Autowired
	RequestValidator requestValidator;
	
	@Autowired
	ControllerHelper controllerHelper;
	
	@Autowired
	BazarbalaEmailImpl bazarbalaEmailImpl;

	@CrossOrigin("/**")
	@PostMapping(value = "/user/genrate/passwordToken")
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response genratePasswordToken(@RequestBody CreateTokenRequest createTokenRequest) {
		Result message = null;
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		String userType = createTokenRequest.getUserType() != null && !createTokenRequest.getUserType().isEmpty()
				? createTokenRequest.getUserType().trim()
				: "";
		String emailId = createTokenRequest.getEmailId() != null && !createTokenRequest.getEmailId().isEmpty()
				? createTokenRequest.getEmailId().trim()
				: ""; 
		String phoneNumber = createTokenRequest.getPhoneNumber() != null
				&& !createTokenRequest.getPhoneNumber().isEmpty() ? createTokenRequest.getPhoneNumber().trim() : "";
		message = requestValidator.validateCreateTokenRequest(phoneNumber, emailId, userType);
		if (!message.isValid()) {
			return builder.status(Response.Status.BAD_REQUEST).entity(message).build();
		}
		message = null;
		try {
			message = adminServices.createToken(phoneNumber, emailId, userType);
			if (message.isValid()) {
				message = bazarbalaEmailImpl.sendSimpleMessage((ForgetPassword) message.getResponse(),createTokenRequest);
			}
		} catch (BazarBalaDAOException bazarBalaDAOException) {
			message = new Result(HttpStatus.OK, bazarBalaDAOException, false);
		}
		builder.status(Response.Status.OK).entity(message);
		return builder.build();

	}
	
	@CrossOrigin("/**")
	@PostMapping(value = "/user/genrate/password")
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response genratePasswordToken(@RequestBody UpdatePassword updatePassword) {
		Result message = null;
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		String token = updatePassword.getToken() != null && ! updatePassword.getToken() .isEmpty()
				?  updatePassword.getToken() .trim()
				: null;
		String password = updatePassword.getPassword() != null && !updatePassword.getPassword().isEmpty()
				? updatePassword.getPassword().trim()
				: null;
		
		
		if (!(token !=null && password !=null)) {
			message = new Result(HttpStatus.BAD_REQUEST, false);
			message.setMessage("token and password is missing ::");
			return builder.status(Response.Status.BAD_REQUEST).entity(message).build();
		}
		message = null;
		try {
			message = adminServices.updatePassword(token,password);
			
		} catch (BazarBalaDAOException bazarBalaDAOException) {
			message = new Result(HttpStatus.OK, bazarBalaDAOException, false);
		}
		builder.status(Response.Status.OK).entity(message);
		return builder.build();

	}
	

    /**
     * Create the Supplier Account who server the orders
     * @param createBusinessUserDTO
     * @return
     */
	@CrossOrigin("/**")
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
			authenticationRequest.setUserType("Supplier");
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
	@CrossOrigin("/**")
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
			authenticationRequest.setUserType("Customer");
			controllerHelper.createToken(authenticationRequest, response);

			builder.status(Response.Status.OK).entity(message);
			return builder.build();
		} else {
			builder.status(Response.Status.BAD_REQUEST).entity(message);
			return builder.build();
		}

	}

}
