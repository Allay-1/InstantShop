package com.bzbala.ad.bazarbala.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationDBService {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String SCHEMA_NAME="bazarbala";
	
	@Autowired
    DataSource dataSource;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public boolean authenticateUserdbs(String phoneNo,String pwdToAuth){

		boolean isUpdated=false;
	    String sql = "SELECT COUNT(*) FROM "+SCHEMA_NAME+".BAZAR_BALA_SUPP_MST WHERE PHONE_NO=? AND PWD=?";
	    
	    isUpdated = jdbcTemplate.queryForObject(sql, new Object[] { phoneNo , pwdToAuth},Boolean.class);
	  
        return isUpdated;
	}

	

	public UserDetails loadUserByUsernameDbs(String phoneNo){

		
	    String sql = "SELECT PHONE_NO,PWD FROM "+SCHEMA_NAME+".BAZAR_BALA_SUPP_MST WHERE PHONE_NO=?";
	    
	    AuthUserUserDTOMapper authUserUserDTOMapper = new AuthUserUserDTOMapper();
	    jdbcTemplate.query(sql, new Object[] { phoneNo }, authUserUserDTOMapper);
	    
	    if(authUserUserDTOMapper != null || authUserUserDTOMapper.getAuthUserUserDTO() == null) {
	    	 sql = "SELECT PHONE_NO,PWD FROM "+SCHEMA_NAME+".BAZAR_BALA_CUST_MST WHERE PHONE_NO=?";
	    	jdbcTemplate.query(sql, new Object[] { phoneNo }, authUserUserDTOMapper);
	    }
	    
	    return new User(authUserUserDTOMapper.getAuthUserUserDTO().getPhoneNo(), 
	    		authUserUserDTOMapper.getAuthUserUserDTO().getPwdToAuth(),
                new ArrayList<>());
  	}
}
