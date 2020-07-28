package com.bzbala.ad.bazarbala.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bzbala.ad.bazarbala.dao.AuthenticationDBService;
import com.bzbala.ad.bazarbala.dao.PersonalInfo;


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
	public PersonalInfo loadUserByUsername(String phoneNo, String userType,boolean isSaveRequer) throws UsernameNotFoundException {

		try {
			return dbOperations.loadUserByUsernameDbs(phoneNo, userType,isSaveRequer);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}
	
//	@Override
//	public void saveCurentOrderByUsername(String phoneNo,String userType) throws UsernameNotFoundException {
//		
//		try
//		{
//		   dbOperations.saveCurentOrderByUsername(phoneNo,userType);
//					
//		}catch (Exception e ) {
//			logger.error(e.getMessage(),e);
//		}
//	
//		
//	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails =null;
		int underscore=username.indexOf("_");
		String phoneNumber=username.substring(0,underscore);
		String userType=username.substring(underscore+1,username.length());
		try
		{
		   PersonalInfo personalInfo=dbOperations.loadUserByUsernameDbs(phoneNumber,userType,false);
		   userDetails = personalInfo.getUserDetail();
					
		}catch (Exception e ) {
			logger.error(e.getMessage(),e);
		}
	
		return userDetails;
	}
	
	
	
}