package com.bzbala.ad.bazarbala.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.bzbala.ad.bazarbala.dto.CreateBusinessUserDTO;
import com.bzbala.ad.bazarbala.dto.Login;
import com.bzbala.ad.bazarbala.dto.Signup;
import com.bzbala.ad.bazarbala.dto.Start;
import com.bzbala.ad.bazarbala.dto.User;

@Controller
public class MyController {

    @GetMapping("/addUser")
    public String sendForm(User user) {
    	return "addUser";
    }

    @PostMapping("/addUser")
    public String processForm(User user) {
        return "showMessage";
    }
    
    
 	
@GetMapping("/login")	
 public String sendForm1(Login login) { 		
    return "login";	
    }	
	
@PostMapping("/login")	
public String processForm1(Login login) {	
    return "paymentPage";	
}	
@GetMapping("/startPage")	
public String sendForm2(Start start) { 		
  return "startPage";	
  }	
@PostMapping("/startPage")	
public String processForm2(Start start) {	
   return "searchResults";	
    }
    
    
}