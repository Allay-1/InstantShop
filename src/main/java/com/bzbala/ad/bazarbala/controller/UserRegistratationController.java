package com.bzbala.ad.bazarbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.bzbala.ad.bazarbala.dto.CreateBusinessUserDTO;
import com.bzbala.ad.bazarbala.dto.User;
import com.bzbala.ad.bazarbala.exception.BazarBalaError;
import com.bzbala.ad.bazarbala.services.AdminServices;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserRegistratationController {

	@Autowired
	AdminServices adminServices;

	@GetMapping("/user/createUser")
	public String getUser(CreateBusinessUserDTO createBusinessUserDTO) {
		return "shopSignup";
	}

	@PostMapping(value = "/user/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public String createUser(CreateBusinessUserDTO createBusinessUserDTO) {

		BazarBalaError message = null;

		if (adminServices.creteBusinessUser(createBusinessUserDTO)) {
			message = new BazarBalaError(HttpStatus.OK);
			message.setMessage("Company has been Successfully created with company ID : "
					+ createBusinessUserDTO.getCustomerId() + " .Pleae keep it secert and use as master admin.");
			// return new ResponseEntity(message, HttpStatus.OK);
			return "shopSignup";
		} else {
			message = new BazarBalaError(HttpStatus.OK);
			message.setMessage("Error While Creating a Company. Please contact to Admin. ");
			// return new ResponseEntity(message, HttpStatus.OK);

			return "successMessage";
		}

	}

}
