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
	 * Basic phone number and email id validation
	 * @param phoneNumber
	 * @param emailId
	 * @return
	 */
	public Result validateCreateTokenRequest(String phoneNumber, String emailId, String userType) {
		String result = null;
		Result message = null;

		if (userType != null && !userType.isEmpty()) {
			if (!(userType.equalsIgnoreCase("Customer") || userType.equalsIgnoreCase("Supplier"))) {
				message = new Result(HttpStatus.BAD_REQUEST, false);
				message.setMessage("UserType is not Matching to our record its value should be Customer or Supplier ::"
						+ userType);
				return message;
			}
		} 
		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			result = BazarbalaUtil.validatePhoneNumber(phoneNumber) ? "Success" : "fail";
			if (result.equalsIgnoreCase("fail")) {
				message = new Result(HttpStatus.BAD_REQUEST, false);
				message.setMessage("Phopne number is not a valid : Please revalidate your number ::" + phoneNumber);
				return message;
			}
		}

		if (emailId != null && !emailId.isEmpty()) {
			result = BazarbalaUtil.isValid(emailId) ? "Success" : "fail";

			if (result.equalsIgnoreCase("fail")) {
				message = new Result(HttpStatus.BAD_REQUEST, false);
				message.setMessage("Email id is not a valid : Please revalidate your email id::" + emailId);
				return message;
			}
		}
		message = new Result(HttpStatus.OK, true);
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
