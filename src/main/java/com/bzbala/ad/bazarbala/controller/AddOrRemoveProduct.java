package com.bzbala.ad.bazarbala.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bzbala.ad.bazarbala.dao.PersonalInfo;
import com.bzbala.ad.bazarbala.order.helper.CreateOrUpdateCustomerOrder;
import com.bzbala.ad.bazarbala.order.model.ItemRequest;
import com.bzbala.ad.bazarbala.order.model.ItemResponse;
import com.bzbala.ad.bazarbala.order.model.OrderResponse;
import com.bzbala.ad.bazarbala.order.model.UpdateOrderRequest;

@Controller
public class AddOrRemoveProduct {

	@Autowired
	CreateOrUpdateCustomerOrder createOrUpdateCustomerOrder;
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/findOrderBySupplierId", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response findCompletedOderBySupplierID(HttpServletRequest request,
			HttpServletResponse response) {
		
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		
		HttpSession session = request.getSession(true);
		PersonalInfo personalInfo=(PersonalInfo) session.getAttribute("PersonaInfo");
		OrderResponse order=createOrUpdateCustomerOrder.getSupplierHistoryOrder(personalInfo.getPersonalId());

		builder.status(Response.Status.OK).entity(order);
		return builder.build();

	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/customer/findCustomerOrder", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response addItemsToBazarwaalaCart(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		HttpSession session = request.getSession(true);
		PersonalInfo personalInfo=(PersonalInfo) session.getAttribute("PersonaInfo");
		OrderResponse order=createOrUpdateCustomerOrder.getCustomerHistoryOrder(personalInfo.getPersonalId());

		builder.status(Response.Status.OK).entity(order);
		return builder.build();

	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/customer/updateOrderRequest", method = RequestMethod.POST)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response addItemsToBazarwaalaCart(@RequestBody UpdateOrderRequest updateOrderRequest, HttpServletRequest request,
			HttpServletResponse response) {
		ItemResponse itemResponse = new ItemResponse();
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		ItemResponse historyResponse=createOrUpdateCustomerOrder.updateHistoryOrder(updateOrderRequest, request, response, itemResponse);

		builder.status(Response.Status.OK).entity(historyResponse);
		return builder.build();

	}

	@CrossOrigin("/**")
	@RequestMapping(value = "/customer/addItem", method = RequestMethod.POST)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response addItemsToBazarwaalaCart(@RequestBody ItemRequest itemRequest, HttpServletRequest request,
			HttpServletResponse response) {
		ItemResponse itemResponse = new ItemResponse();
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		createOrUpdateCustomerOrder.createOrder(itemRequest, request, response, itemResponse);

		builder.status(Response.Status.OK).entity(itemResponse);
		return builder.build();

	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/customer/getItems", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getBazqarwalaCat(HttpServletRequest request,
			HttpServletResponse response) {
		ItemResponse itemResponse = new ItemResponse();
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		createOrUpdateCustomerOrder.getOrder(request, response, itemResponse);

		builder.status(Response.Status.OK).entity(itemResponse);
		return builder.build();

	}

}
