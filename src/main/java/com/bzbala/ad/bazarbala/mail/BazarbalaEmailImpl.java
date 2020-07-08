package com.bzbala.ad.bazarbala.mail;

import java.util.Properties;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.bzbala.ad.bazarbala.dto.CreateTokenRequest;
import com.bzbala.ad.bazarbala.dto.ForgetPassword;
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.exception.Result;

@Service
public class BazarbalaEmailImpl {
	 @Autowired
	 private JavaMailSender emailSender;
	 
	 @Value("${spring.mail.username}")
	 private String username;
	 
	 @Value("${spring.mail.password}")
	 private String password;
	 
	 @Value("${spring.mail.host}")
	 private String host;
	 
	 @Value("${spring.mail.port}")
	 private int port;
	 
	
	/**
	 * Send message 
	 * @param frogetPassword
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public Result sendSimpleMessage(ForgetPassword frogetPassword, CreateTokenRequest createTokenRequest)
			throws BazarBalaDAOException {
		Result message = null;
		message = new Result(HttpStatus.OK, true);
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			String emailID = null;
			if (frogetPassword.getEmailId() == null || frogetPassword.getEmailId().isEmpty()) {
				emailID = "allay.techie@gmail.com";
				message.setMessage(
						"Your Token is sent to Admin mail As your recorde dose not have email id please contact us. please have your home id or shop id handy with you for validation");
			} else {
				emailID = frogetPassword.getEmailId();
				message.setMessage("Your Token is sent to mail please use token to set up your password");
			}

			simpleMailMessage.setFrom(username);
			simpleMailMessage.setTo(emailID);
			simpleMailMessage.setSubject("Forget Password Token");
			simpleMailMessage.setText(frogetPassword.getToken());
			emailSender.send(simpleMailMessage);

			message.setValid(true);
		} catch (Exception exception) {
			throw new BazarBalaDAOException(exception.getMessage(), exception.toString());
		}
		return message;
	}
	
	/**
	 * setting mail basic detail
	 * @return
	 */
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("");
	    mailSender.setPassword("");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.debug", true);
	     
	    return mailSender;
	}

}
