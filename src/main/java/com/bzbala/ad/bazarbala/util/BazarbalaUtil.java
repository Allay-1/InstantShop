package com.bzbala.ad.bazarbala.util;


public class BazarbalaUtil {
	
	
	private BazarbalaUtil() {}
 	
	public static final long generateRandomID(int len) {
	  
		if (len > 18)
	        throw new IllegalStateException("To many digits");
	    long tLen = (long) Math.pow(10, len - 1) * 9;

	    return (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;
	}
	
	
	public static final String generatePwdEnc(String pwdStr) {
	return pwdStr + "@123";
	}
}
