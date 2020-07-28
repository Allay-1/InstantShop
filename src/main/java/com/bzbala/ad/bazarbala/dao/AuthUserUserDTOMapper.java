package com.bzbala.ad.bazarbala.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import com.bzbala.ad.bazarbala.dto.AuthUserUserDTO;


public class AuthUserUserDTOMapper implements RowMapper<AuthUserUserDTO>{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	AuthUserUserDTO  authUserUserDTO;

	@Override
	public AuthUserUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		authUserUserDTO = new AuthUserUserDTO();

		authUserUserDTO.setPhoneNo(rs.getString("PHONE_NO"));
		authUserUserDTO.setPwdToAuth(rs.getString("PWD"));
	
		if(isThere(rs,"SUPPLIER_ID") && isThere(rs,"SHOP_ID")) {
			authUserUserDTO.setPersonalId(rs.getString("SUPPLIER_ID"));
			authUserUserDTO.setId(String.valueOf(rs.getInt("SHOP_ID")));
			authUserUserDTO.setUserType("Supplier");
		}else if (isThere(rs,"CUSTOMER_ID")&& isThere(rs,"HOME_ID")) {
			authUserUserDTO.setPersonalId(rs.getString("CUSTOMER_ID"));
			authUserUserDTO.setId(String.valueOf(rs.getInt("HOME_ID")));
			authUserUserDTO.setAddress(rs.getString("ADDRESS"));
			authUserUserDTO.setPhoneNo(rs.getString("PHONE_NO"));
			authUserUserDTO.setNextOrderId(Integer.valueOf(rs.getInt("NEXTORDERNO")));
			authUserUserDTO.setUserType("Customer");
		}
		

		return authUserUserDTO;
	}

	public AuthUserUserDTO getAuthUserUserDTO() {
		return authUserUserDTO;
	}
	
	private boolean isThere(ResultSet rs, String column){
	    try{
	        rs.findColumn(column);
	        return true;
	    } catch (SQLException sqlex){
	      // logger.debug("column doesn't exist {}", column);
	    }

	    return false;
	}
	
	
}
