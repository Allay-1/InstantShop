package com.bzbala.ad.bazarbala.exception;

public class BazarBalaDAOException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String methodName;

	public BazarBalaDAOException(){
		super();
	}
	
	public BazarBalaDAOException(String message,String methodName){
		super(message);
		this.methodName = methodName;
	}
	
	public BazarBalaDAOException(String message,String methodName,Throwable cause){
		super(message,cause);
		this.methodName = methodName;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	
	@Override
	public String toString(){
		return super.toString();
	}
	
   @Override
	public String getMessage(){
		return super.getMessage() + "for method "+this.methodName;
	}

}
