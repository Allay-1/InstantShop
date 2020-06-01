package com.bzbala.ad.bazarbala.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzbala.ad.bazarbala.constant.APPLICATION_CONSTANTS;
import com.bzbala.ad.bazarbala.dao.DBServices;
import com.bzbala.ad.bazarbala.dto.CreateBusinessUserDTO;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;

@Service
public class AdminServicesImpl implements AdminServices {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DBServices dbOperations;

	public boolean creteBusinessUser(CreateBusinessUserDTO createBusinessUserDTO) {

		createBusinessUserDTO.setCustomerId(BazarbalaUtil.generateRandomID(APPLICATION_CONSTANTS.CUSTOMER_ID_LENGHT));
		createBusinessUserDTO.setShopId(BazarbalaUtil.generateRandomID(APPLICATION_CONSTANTS.CUSTOMER_ID_LENGHT));
		createBusinessUserDTO
				.setShopOwnerPwd(BazarbalaUtil.generatePwdEnc(createBusinessUserDTO.getShopOwnerEmailId()));
		boolean rsStatus = false;

		try {
			rsStatus = dbOperations.creteBusinessUser(createBusinessUserDTO);
		} catch (Exception e) {
			logger.info("SOMETHING WENT WRONG IN BACKEND CALL");
		}

		return rsStatus;

	}
}