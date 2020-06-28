package com.bzbala.ad.bazarbala.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.bzbala.ad.bazarbala.dto.AuthUserUserDTO;


public class AuthUserUserDTOMapper implements RowMapper<AuthUserUserDTO>{
	
	AuthUserUserDTO  authUserUserDTO;

	@Override
	public AuthUserUserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		   authUserUserDTO = new AuthUserUserDTO();
		   
		   authUserUserDTO.setPhoneNo(rs.getString("PHONE_NO"));
		   authUserUserDTO.setPwdToAuth(rs.getString("PWD"));
			
		   return authUserUserDTO;
	}

	public AuthUserUserDTO getAuthUserUserDTO() {
		return authUserUserDTO;
	}	
	
	
}
