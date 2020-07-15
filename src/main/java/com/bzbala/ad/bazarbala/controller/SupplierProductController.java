package com.bzbala.ad.bazarbala.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.validator.ProductRequestValidator;
import com.bzbala.ad.bazarbala.product.model.Category;
import com.bzbala.ad.bazarbala.product.model.ProductClientRequest;
import com.bzbala.ad.bazarbala.product.model.ProductDetailResponse;
import com.bzbala.ad.bazarbala.product.model.ProductRequest;
import com.bzbala.ad.bazarbala.product.model.ProductResponse;
import com.bzbala.ad.bazarbala.product.service.ProductService;


@Controller
public class SupplierProductController {
	

	@Autowired
	ProductRequestValidator requestValidator;
	
	@Autowired
	ProductService productService;

	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/saveProduct", method = RequestMethod.POST)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response saveProduct(@RequestBody ProductRequest productRequest) throws BazarBalaDAOException {

		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		List<ProductClientRequest> productlistInRequest = new ArrayList<>();

		Map<String, String> sucessResponse = new HashMap<>();
		Map<String, String> erroResponse = new HashMap<>();
        if(productRequest.getProductRequest() != null) {
		productRequest.getProductRequest().stream().forEach(item -> {

			Result validatorResult = requestValidator.validateProductRequest(item);
			if (validatorResult.isValid()) {
				productlistInRequest.add(item);
				sucessResponse.put(item.getProductCode(), "Success");
			} else {
				erroResponse.put(item.getProductCode() != null ? item.getProductCode() : item.getCategoryCode(),
						validatorResult.getMessage());

			}
		});

		productService.createProductRequest(productlistInRequest);
		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductImportFail(erroResponse);
		productResponse.setProductImportSuccess(sucessResponse);
		builder.status(Response.Status.OK).entity(productResponse);
        }else {
        	builder.status(Response.Status.BAD_REQUEST).entity("Missing Product Request");
        }
		return builder.build();

	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/getAllProduct", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getProduct() throws BazarBalaDAOException {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		ProductDetailResponse productDetailResponse=productService.findAll();
		builder.status(Response.Status.OK).entity(productDetailResponse);
		return builder.build();
		
	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/getAllProduct/{supplierId}", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getProductBySupplierID(@PathVariable("supplierId") String supplierId) throws BazarBalaDAOException {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		ProductDetailResponse productDetailResponse=productService.findAllBySupplierId(supplierId);
		builder.status(Response.Status.OK).entity(productDetailResponse);
		return builder.build();
		
	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/getAllProduct/{supplierId}/{categoryId}", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getProductBySupplierIDAndCategoryId(@PathVariable("supplierId") String supplierId,@PathVariable("categoryId") String CategoryCode) throws BazarBalaDAOException {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		ProductDetailResponse productDetailResponse=productService.findAllBySupplierIdAndCategoryId(supplierId,CategoryCode);
		builder.status(Response.Status.OK).entity(productDetailResponse);
		return builder.build();
		
	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/categoryDetail/{categoryId}", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getProductBySupplierIDAndCategoryId(@PathVariable("categoryId") String categoryId) throws BazarBalaDAOException {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		Category category=productService.findAllByCategoryCode(categoryId);
		builder.status(Response.Status.OK).entity(category);
		return builder.build();
		
	}
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/deleteProduct/{productId}", method = RequestMethod.GET)
	@ResponseBody
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response deleteProduct(@PathVariable("productId") String productId) throws BazarBalaDAOException {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		productService.deleteByProudctId(productId);
		builder.status(Response.Status.OK).entity("sucess");
		return builder.build();
		
	}
	
	

}
