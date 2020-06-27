package com.bzbala.ad.bazarbala.util;

import java.util.regex.Pattern;

public final class BazarbalaUtil {
	
 	/**
 	 * 
 	 * @param len
 	 * @return
 	 */
	public static final long generateRandomID(int len) {

		if (len > 18)
			throw new IllegalStateException("To many digits");
		long tLen = (long) Math.pow(10, len - 1) * 9;

		return (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;
	}
	
	/**
	 * 
	 * @param pwdStr
	 * @return
	 */
	public static final String generatePwdEnc(String pwdStr) {
		return pwdStr + "@123";
	}
	
	/**
	 * 
	 * @param phoneNo
	 * @return
	 */
	public static boolean validatePhoneNumber(String phoneNo) {
		//validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}")) return true;
		//validating phone number with -, . or spaces
		else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
		//validating phone number with extension length from 3 to 5
		else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
		//validating phone number where area code is in braces ()
		else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
		//return false if nothing matches the input
		else return false;
		
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
}
