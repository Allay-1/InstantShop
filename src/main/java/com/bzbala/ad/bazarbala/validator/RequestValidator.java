package com.bzbala.ad.bazarbala.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.bzbala.ad.bazarbala.dto.InstantShopCustomer;
import com.bzbala.ad.bazarbala.dto.InstantShopSupplier;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;

@Component
public class RequestValidator {
	
	public Result validateSupplierRequest(InstantShopSupplier createBusinessUserDTO) {

		String result = null;
		Result message = null;
		result = BazarbalaUtil.validatePhoneNumber(createBusinessUserDTO.getPhoneNnumber()) ? "Success" : "fail";

		if (result.equalsIgnoreCase("fail")) {
			message = new Result(HttpStatus.BAD_REQUEST,false);
			message.setMessage("Phopne number is not a valid : Please revalidate your number ::"
					+ createBusinessUserDTO.getPhoneNnumber());
			return message;
		}

		if (createBusinessUserDTO.getEmailId() != null && !createBusinessUserDTO.getEmailId().isEmpty()) {
			result = BazarbalaUtil.isValid(createBusinessUserDTO.getEmailId()) ? "Success" : "fail";

			if (result.equalsIgnoreCase("fail")) {
				message = new Result(HttpStatus.BAD_REQUEST,false);
				message.setMessage("Email id is not a valid : Please revalidate your email id::"
						+ createBusinessUserDTO.getEmailId());
				return message;
			}
		}
		message = new Result(HttpStatus.OK,true);
		return message;
	}
	
	/**
	 * 
	 * @param createBusinessUserDTO
	 * @return
	 */
	public Result validateCustomerRequest(InstantShopCustomer createBusinessUserDTO) {

		String result = null;
		Result message = null;
		result = BazarbalaUtil.validatePhoneNumber(createBusinessUserDTO.getPhoneNnumber()) ? "Success" : "fail";

		if (result.equalsIgnoreCase("fail")) {
			message = new Result(HttpStatus.BAD_REQUEST,false);
			message.setMessage("Phopne number is not a valid : Please revalidate your number ::"
					+ createBusinessUserDTO.getPhoneNnumber());
			return message;
		}

		if (createBusinessUserDTO.getEmailId() != null && !createBusinessUserDTO.getEmailId().isEmpty()) {
			result = BazarbalaUtil.isValid(createBusinessUserDTO.getEmailId()) ? "Success" : "fail";

			if (result.equalsIgnoreCase("fail")) {
				message = new Result(HttpStatus.BAD_REQUEST,false);
				message.setMessage("Email id is not a valid : Please revalidate your email id::"
						+ createBusinessUserDTO.getEmailId());
				return message;
			}
		}
		message = new Result(HttpStatus.OK,true);
		return message;
	}

}
