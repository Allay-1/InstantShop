package com.bzbala.ad.bazarbala.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import com.bzbala.ad.bazarbala.dao.PersonalInfo;
import com.bzbala.ad.bazarbala.dto.AuthenticationRequest;
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

	@CrossOrigin("/**")
	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		Result message = null;
		HttpSession session = request.getSession(true);
		session.setAttribute("PersonaInfo", null);
		response.addHeader("Authorization", null);
		response.addHeader("User_Type", null);
		message = new Result(HttpStatus.OK, true);
		message.setMessage("Success: ");
		builder.status(Response.Status.OK).entity(message);
		return builder.build();

	}

	@CrossOrigin("/**")
	@RequestMapping(value = "/user/authAndCreateToken", method = RequestMethod.POST)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		Result message = null;
		if (authenticationRequest.getUserType() != null && !authenticationRequest.getUserType().isEmpty()) {
			if (!(authenticationRequest.getUserType().equalsIgnoreCase("Customer")
					|| authenticationRequest.getUserType().equalsIgnoreCase("Supplier")
					|| authenticationRequest.getUserType().equalsIgnoreCase("Admin"))) {
				message = new Result(HttpStatus.BAD_REQUEST, false);
				message.setMessage("UserType is not Matching to our record its value should be Customer or Supplier ::"
						+ authenticationRequest.getUserType());
				builder.status(Response.Status.BAD_REQUEST).entity(message);
				return builder.build();
			}
		} else {
			message = new Result(HttpStatus.BAD_REQUEST, false);
			message.setMessage("UserType is missing in Request ::");
			builder.status(Response.Status.BAD_REQUEST).entity(message);
			return builder.build();
		}
		Authentication auth = null;
		try {
			SimpleGrantedAuthority authority = null;
			List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
			if (authenticationRequest.getUserType().equalsIgnoreCase("Supplier")) {
				authority = new SimpleGrantedAuthority("ROLE_SUPPLIER");
				updatedAuthorities.add(authority);
			} else if (authenticationRequest.getUserType().equalsIgnoreCase("Customer")) {
				authority = new SimpleGrantedAuthority("ROLE_CUSTOMER");
				updatedAuthorities.add(authority);
			} else if (authenticationRequest.getUserType().equalsIgnoreCase("Admin")) {
				updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_SUPPLIER"));
				updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
			}
	//		List<SimpleGrantedAuthority> updatedAuthorities= Arrays.asList(new SimpleGrantedAuthority("USER_TYPE_CUSTOMER"),new SimpleGrantedAuthority("USER_TYPE_SUPPLIER"));
			
			updatedAuthorities.add(authority);
			//updatedAuthorities.add(new SimpleGrantedAuthority("USER_TYPE_CUSTOMER"));
			auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getPhoneNo() + "_" + authenticationRequest.getUserType(),
					BazarbalaUtil.generatePwdEnc(authenticationRequest.getPassword()), updatedAuthorities));

		} catch (BadCredentialsException e) {
			message = new Result(HttpStatus.OK, e, false);
			builder.status(Response.Status.BAD_REQUEST).entity(message);
			return builder.build();
		}
		if (auth.isAuthenticated()) {
			PersonalInfo personalInfo = authenticationServiceObj.loadUserByUsername(authenticationRequest.getPhoneNo(),
					authenticationRequest.getUserType(),true);

			final String jwt = "Bearer " + jwtTokenUtil.generateToken(personalInfo.getUserDetail());
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(auth);
			HttpSession session = request.getSession(true);
			personalInfo.setUserDetail(null);
			session.setAttribute("PersonaInfo", personalInfo);
			message = new Result(HttpStatus.OK, true);
			message.setMessage("Success: ");
			response.addHeader("Authorization", jwt);
			response.addHeader("User_Type", personalInfo.getUserType());
		}
		builder.status(Response.Status.OK).entity(message);
		return builder.build();
	}

	/**
	 * 
	 * @param authenticationRequest
	 * @param response
	 * @return
	 */
	public Result createToken(AuthenticationRequest authenticationRequest, HttpServletResponse response) {
		Result message = null;
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhoneNo(),
							BazarbalaUtil.generatePwdEnc(authenticationRequest.getPassword())));
		} catch (BadCredentialsException e) {
			message = new Result(HttpStatus.OK, e, false);

			return message;
		}

		PersonalInfo personalInfo = authenticationServiceObj.loadUserByUsername(authenticationRequest.getPhoneNo(),
				authenticationRequest.getUserType(),false);

		final String jwt = "Bearer " + jwtTokenUtil.generateToken(personalInfo.getUserDetail());

		message = new Result(HttpStatus.OK, true);
		message.setMessage("Success: ");
		response.addHeader("Authorization", jwt);
		return message;
	}
}
