package com.bzbala.ad.bazarbala.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bzbala.ad.bazarbala.dao.PersonalInfo;

public interface AuthenticationService {

	public boolean authenticateUserSer(String userId, String pwdToAuth, String userType);

	public PersonalInfo loadUserByUsername(String phoneNumber, String userType,boolean isSaveRequire) throws UsernameNotFoundException;
	
	//public void saveCurentOrderByUsername(String phoneNo,String userType) throws UsernameNotFoundException;
}
