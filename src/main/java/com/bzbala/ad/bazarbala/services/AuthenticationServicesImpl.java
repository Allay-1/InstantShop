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
	public boolean authenticateUserSer(String userId, String pwdToAuth) {
		boolean isUpdated = false;
		try
		{
		}catch (Exception e ) {
			logger.error(e.getMessage(),e);
		}
		return isUpdated;
		
	}
	@Override
	public UserDetails loadUserByUsername(String phoneNo) throws UsernameNotFoundException {
		UserDetails userDetails =null;
		try
		{
		   userDetails = dbOperations.loadUserByUsernameDbs(phoneNo);
					
		}catch (Exception e ) {
			logger.error(e.getMessage(),e);
		}
	
		return userDetails;
	}
	
	
	
}