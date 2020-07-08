package com.bzbala.ad.bazarbala.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bzbala.ad.bazarbala.dao.AuthenticationDBService;


@Service
public class AuthenticationServicesImpl implements AuthenticationService, UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
    private AuthenticationDBService dbOperations;
	
	
	@Override
	public boolean authenticateUserSer(String userId, String pwdToAuth,String userType) {
		boolean isUpdated = false;
		try
		{
		}catch (Exception e ) {
			logger.error(e.getMessage(),e);
		}
		return isUpdated;
		
	}
	@Override
	public UserDetails loadUserByUsername(String phoneNo,String userType) throws UsernameNotFoundException {
		UserDetails userDetails =null;
		try
		{
		   userDetails = dbOperations.loadUserByUsernameDbs(phoneNo,userType);
					
		}catch (Exception e ) {
			logger.error(e.getMessage(),e);
		}
	
		return userDetails;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails =null;
		int underscore=username.indexOf("_");
		String phoneNumber=username.substring(0,underscore);
		String userType=username.substring(underscore+1,username.length());
		try
		{
		   userDetails = dbOperations.loadUserByUsernameDbs(phoneNumber,userType);
					
		}catch (Exception e ) {
			logger.error(e.getMessage(),e);
		}
	
		return userDetails;
	}
	
	
	
}