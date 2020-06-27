package com.bzbala.ad.bazarbala.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzbala.ad.bazarbala.constant.APPLICATION_CONSTANTS;
import com.bzbala.ad.bazarbala.dao.DBServices;
import com.bzbala.ad.bazarbala.dto.Address;
import com.bzbala.ad.bazarbala.dto.InstantShopCustomer;
import com.bzbala.ad.bazarbala.dto.InstantShopSupplier;
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;

@Service
public class AdminServicesImpl implements AdminServices {

	@Autowired
	private DBServices dbOperations;
    /**
     * 
     */
	@Override
	public Result creteSupplier(InstantShopSupplier createBusinessUserDTO) throws BazarBalaDAOException {

		createBusinessUserDTO.setSupplierId(BazarbalaUtil.generateRandomID(APPLICATION_CONSTANTS.CUSTOMER_ID_LENGHT));
		createBusinessUserDTO.setShopId(BazarbalaUtil.generateRandomID(APPLICATION_CONSTANTS.CUSTOMER_ID_LENGHT));
		createBusinessUserDTO.setAddressId(BazarbalaUtil.generateRandomID(APPLICATION_CONSTANTS.CUSTOMER_ID_LENGHT));
		createBusinessUserDTO
				.setSupplierPassword(BazarbalaUtil.generatePwdEnc(createBusinessUserDTO.getSupplierPassword()));
		Address address = new Address();
		address.setAddressId(createBusinessUserDTO.getAddressId());
		address.setShopAddress(createBusinessUserDTO.getAddress());
		address.setZipCode(createBusinessUserDTO.getZipCode());
		address.setId(String.valueOf(createBusinessUserDTO.getSupplierId()));
		address.setName(createBusinessUserDTO.getShopName());

		return dbOperations.creteBusinessUser(createBusinessUserDTO, address);

	}

	@Override
	public Result creteCustomer(InstantShopCustomer createBusinessUserDTO) throws BazarBalaDAOException {

		createBusinessUserDTO.setCustomerId(BazarbalaUtil.generateRandomID(APPLICATION_CONSTANTS.CUSTOMER_ID_LENGHT));
		createBusinessUserDTO.setHomeId(BazarbalaUtil.generateRandomID(APPLICATION_CONSTANTS.CUSTOMER_ID_LENGHT));
		createBusinessUserDTO.setAddressId(BazarbalaUtil.generateRandomID(APPLICATION_CONSTANTS.CUSTOMER_ID_LENGHT));
		createBusinessUserDTO.setCustomerPwd(BazarbalaUtil.generatePwdEnc(createBusinessUserDTO.getCustomerPwd()));

		Address address = new Address();
		address.setAddressId(createBusinessUserDTO.getAddressId());
		address.setShopAddress(createBusinessUserDTO.getAddress());
		address.setZipCode(createBusinessUserDTO.getZipCode());
		address.setId(String.valueOf(createBusinessUserDTO.getCustomerId()));
		address.setName(createBusinessUserDTO.getFirstName());
		return dbOperations.creteCustomerUser(createBusinessUserDTO, address);

	}
}