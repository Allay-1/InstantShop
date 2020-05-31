package com.bzbala.ad.bazarbala.dto;

public class Signup {

    private String shopname;
    private String ownername;
    private String zipcode;
    private String shoptype;
    private String password;
    private String confirmpassword;
    private String username;
    private String address;
    
  
    public String getUsername() {
       return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

	public String setPassword(String password) {
        return this.password = password;
    }
	public String getShopname() {
	       return shopname;
	    }

	    public void setShopname(String shopname) {
	        this.shopname = shopname;
	    }
	    public String getOwnername() {
	        return ownername;
	     }

	     public void setOwnername(String ownername) {
	         this.ownername = ownername;
	     }
	     public String getShoptype() {
	         return shoptype;
	      }

	      public void setShoptype(String shoptype) {
	          this.shoptype = shoptype;
	      }
	      public String getConfirmpassword() {
	          return confirmpassword;
	       }

	       public void setConfirmpassword(String confirmpassword) {
	           this.confirmpassword = confirmpassword;
	       }
	       public String getZipcode() {
	           return zipcode;
	        }

	        public void setZipcode(String zipcode) {
	            this.zipcode = zipcode;
	        }
	        
	        public String getAddress() {
	    		return address;
	    	}

	    	public void setAddress(String address) {
	    		this.address = address;
	    	}
}