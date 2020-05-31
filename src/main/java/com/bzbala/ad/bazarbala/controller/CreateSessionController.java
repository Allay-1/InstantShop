package com.bzbala.ad.bazarbala.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bzbala.ad.bazarbala.dto.UserRegistration;

@RestController
@RequestMapping ("/bzbl/rest/v1/createSession")
public class CreateSessionController {
	
	

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE )
  public void createSession(@RequestBody UserRegistration userReg) {
	 System.out.println(userReg.getShop());
  }
  
  
}
