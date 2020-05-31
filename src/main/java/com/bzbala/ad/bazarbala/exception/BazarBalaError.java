package com.bzbala.ad.bazarbala.exception;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;


import com.fasterxml.jackson.annotation.JsonFormat;

public class BazarBalaError {
	
	
	private HttpStatus status;
	
	   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	   private Date timestamp;
	   private String message;
	   private String debugMessage;
	   private List<BazarBalaSubError> subErrors;

	
	   
	   private BazarBalaError() {
	       timestamp = new Date();
	   }
	   
	   public BazarBalaError(HttpStatus status) {
	       this();
	       this.status = status;
	   }
	   
	   public BazarBalaError(HttpStatus status, Throwable ex) {
	       this();
	       this.status = status;
	       this.message = "Unexpected error";
	       this.debugMessage = ex.getLocalizedMessage();
	   }
	   
	   public BazarBalaError(HttpStatus status, String message, Throwable ex) {
	       this();
	       this.status = status;
	       this.message = message;
	       this.debugMessage = ex.getLocalizedMessage();
	   }
	   
	   public BazarBalaError(HttpStatus status, String message) {
	       this();
	       this.status = status;
	       this.message = message;
	   }
	   
	   
   public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public List<BazarBalaSubError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<BazarBalaSubError> subErrors) {
		this.subErrors = subErrors;
	}


	abstract class BazarBalaSubError {

	   }
	   
	   class ApiValidationError extends BazarBalaSubError {
		   
		   private String object;
		   private String field;
		   private Object rejectedValue;
		   private String message;

		   ApiValidationError(String object, String message) {
		       this.object = object;
		       this.message = message;
		   }
	   }
}
