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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.product.model.ProductClientRequest;
import com.bzbala.ad.bazarbala.product.model.ProductRequest;
import com.bzbala.ad.bazarbala.product.model.ProductResponse;
import com.bzbala.ad.bazarbala.product.service.ProductService;
import com.bzbala.ad.bazarbala.product.service.ProductUploadService;
import com.bzbala.ad.bazarbala.validator.ProductRequestValidator;

@Controller
public class UploadSupplierProductFile {
	
	@Autowired
	ProductUploadService productUploadService;
	
	@Autowired
	ProductRequestValidator requestValidator;
	
	@Autowired
	ProductService productService;
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/uploadProduct", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	@ResponseBody
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response uploadProduct(@RequestParam("productFile") MultipartFile productFile) throws BazarBalaDAOException {
		
		List<Map<String, String>> listOfProduct=productUploadService.uploadProductFile(productFile);
		ProductRequest productRequest=productUploadService.getProductRequest(listOfProduct);
		
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

}
