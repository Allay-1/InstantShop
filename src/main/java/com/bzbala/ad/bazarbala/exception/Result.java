package com.bzbala.ad.bazarbala.exception;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
	
	
	private HttpStatus status;
	
	   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	   private Date timestamp;
	   private String message;
	   private String debugMessage;
	   private boolean valid;
	   @JsonIgnore
	   private Object response;
	   @JsonIgnore
	   private List<BazarBalaSubError> subErrors;

	
	   
	   private Result() {
	       timestamp = new Date();
	   }
	   
	   public Result(HttpStatus status,boolean valid) {
	       this();
	       this.valid = valid;
	       this.status = status;
	   }
	   
	   public Result(HttpStatus status, Throwable ex,boolean valid) {
	       this();
	       this.status = status;
	       this.valid = valid;
	       this.message = "Unexpected error";
	       this.debugMessage = ex.getLocalizedMessage();
	   }
	   
	   public Result(HttpStatus status, String message, Throwable ex,boolean valid) {
	       this();
	       this.status = status;
	       this.message = message;
	       this.valid = valid;
	       this.debugMessage = ex.getLocalizedMessage();
	   }
	   
	   public Result(HttpStatus status, String message,boolean valid) {
	       this();
	       this.status = status;
	       this.valid = valid;
	       this.message = message;
	   }
	   
	   public Result(HttpStatus status, String message,boolean valid,Object response) {
	       this();
	       this.status = status;
	       this.valid = valid;
	       this.message = message;
	       this.response = response;
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
    

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
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

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	   
}
