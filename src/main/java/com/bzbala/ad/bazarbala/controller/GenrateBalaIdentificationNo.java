package com.bzbala.ad.bazarbala.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bzbala.ad.bazarbala.exception.Result;

@Controller
public class GenrateBalaIdentificationNo {

	@CrossOrigin("/**")
	@RequestMapping(value = "/genrateBin", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response genrateBIN(HttpServletRequest request, HttpServletResponse response) {
		boolean isCookieAvailable = false;
		Result message = null;
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		String cokieName = "BIN";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cokieName.equals(cookie.getName())) {
					if (cookie.getValue() != null && !cookie.getValue().isEmpty()) {
						isCookieAvailable = true;
						break;
					}
				}
			}
		}
		if (!isCookieAvailable) {
			
			String generatedString = RandomStringUtils.randomAlphanumeric(16);
		//	request.getSession().setAttribute("MY_SESSION_MESSAGES", generatedString);
			Cookie binCookie = new Cookie("BIN", generatedString);
			binCookie.setMaxAge(30 * 60);
			response.addCookie(binCookie);

		}
		message = new Result(HttpStatus.OK, true);
		message.setMessage("Success::");
		builder.status(Response.Status.OK).entity(message);
		return builder.build();
	}
	
	@CrossOrigin("/**")   
	@RequestMapping(value = "/removeBin", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response removeBIN(HttpServletRequest request, HttpServletResponse response) {
		Result message = null;
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		String cokieName = "BIN";
		Cookie cookie = new Cookie(cokieName, "");
	    cookie.setMaxAge(0);
	    response.addCookie(cookie);
		message = new Result(HttpStatus.OK, true);
		message.setMessage("Success ::");
		builder.status(Response.Status.OK).entity(message);
		return builder.build();
	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/destroySession", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response destroySession(HttpServletRequest request) {
		Result message = null;
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		String messages = (String) request.getSession().getAttribute("MY_SESSION_MESSAGES");
	//	System.out.print("1111111111+"+messages);
		request.getSession().invalidate();
		message = new Result(HttpStatus.OK, true);
		message.setMessage("Success ::");
		builder.status(Response.Status.OK).entity(message);
		return builder.build();
	}

}
