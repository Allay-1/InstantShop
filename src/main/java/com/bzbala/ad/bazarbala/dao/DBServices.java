package com.bzbala.ad.bazarbala.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bzbala.ad.bazarbala.dto.Address;
import com.bzbala.ad.bazarbala.dto.ForgetPassword;
import com.bzbala.ad.bazarbala.dto.InstantShopCustomer;
import com.bzbala.ad.bazarbala.dto.InstantShopSupplier;
import com.bzbala.ad.bazarbala.dto.PhoneAndEmail;
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.exception.Result;
import com.bzbala.ad.bazarbala.model.UserReposistory;

import java.util.*;



@Repository
public class DBServices {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String SCHEMA_NAME="bazarbala";
	
	@Autowired
    DataSource dataSource;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * create the Supplier
	 * @param createBusinessUserDTO
	 * @param address
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public Result creteBusinessUser(InstantShopSupplier createBusinessUserDTO,Address address) throws BazarBalaDAOException {

		Result message=null;
		String sqlPhopne="SELECT * FROM bazarbala.BAZAR_BALA_SUPP_MST WHERE PHONE_NO=?";
		message=isValidPhonNumber(createBusinessUserDTO.getPhoneNnumber(),sqlPhopne);
		if(!message.isValid()) {
			return message;
		}
		try {
			dataSource.getConnection().setAutoCommit(false);
			int count = 0;

			String sql = "INSERT INTO " + SCHEMA_NAME
					+ ".BAZAR_BALA_SUPP_MST(SHOP_ID,SUPPLIER_ID,ADDRESS_ID,SHOP_NAME,ADDRESS,ZIPCODE,PHONE_NO,EMAIL_ID,PWD,CATEGORY)\n"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?)";

			count = jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

					PreparedStatement ps = connection.prepareStatement(sql);

					ps.setLong(1, createBusinessUserDTO.getShopId());
					ps.setString(2, String.valueOf(createBusinessUserDTO.getSupplierId()));
					ps.setString(3, String.valueOf(createBusinessUserDTO.getAddressId()));
					ps.setString(4, createBusinessUserDTO.getShopName());
					ps.setString(5, createBusinessUserDTO.getAddress());
					ps.setString(6, createBusinessUserDTO.getZipCode());
					ps.setString(7, createBusinessUserDTO.getPhoneNnumber());
					ps.setString(8, createBusinessUserDTO.getEmailId());
					ps.setString(9, createBusinessUserDTO.getSupplierPassword());
					ps.setString(10, createBusinessUserDTO.getCategory());
					
					return ps;
				}
			});

			if (count > 0) {
				message=updateAddress(address,"Supplier");
				if(message.isValid()) {
					message.setMessage("Company has been Successfully created with company ID : "
							+ createBusinessUserDTO.getSupplierId() + " .Pleae keep it secert and use as master admin.");
				}
			}else {
				message = new Result(HttpStatus.BAD_REQUEST,false);
				message.setMessage("Error While Creating a Company. Please contact to Admin. ");
				message.setValid(false);
			}

		} catch (SQLException sqlException) {
			logger.info("Error while customer {} for user Type {}", sqlException);
			throw new BazarBalaDAOException(sqlException.getMessage(), sqlException.getSQLState());
		} catch (Exception exception) {
			logger.info("Error while customer un_known exception {} for user Type {}",exception);
			throw new BazarBalaDAOException(exception.getMessage(), exception.getLocalizedMessage());
		}

		return message;
	}
	
	/**
	 * Validate the phone in database , every user should have unique phone no. 
	 * @param phoneNumber
	 * @param sql
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public Result isValidPhonNumber(String phoneNumber, String sql) throws BazarBalaDAOException {

		Result message = null;

		try {
			dataSource.getConnection().setAutoCommit(false);
           
			List<String> phoneList = jdbcTemplate.query(sql, new Object[] { phoneNumber }, new RowMapper<String>() {
				public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
					return resultSet.getString("PHONE_NO");
				}

			});

			if (phoneList.size() > 0) {
				message = new Result(HttpStatus.BAD_REQUEST, false);
				message.setMessage(
						"Your phone no. is already registed in our system in case if you dont remember login credential please use forget password ");

			} else {
				message = new Result(HttpStatus.OK, true);

			}

		} catch (SQLException sqlException) {
			logger.info("Error while customer {} for user Type {}", sqlException);
			throw new BazarBalaDAOException(sqlException.getMessage(), sqlException.getSQLState());
		} catch (Exception exception) {
			logger.info("Error while customer un_known exception {} for user Type {}", exception);
			throw new BazarBalaDAOException(exception.getMessage(), exception.getLocalizedMessage());
		}

		return message;
	}
	
	/**
	 * Validate the phone in database , every user should have unique phone no. 
	 * @param phoneNumber
	 * @param sql
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public Result isValidAndUpdaePhonNumber(String phoneNumber,String email,String token,Long timeStamp,String userType) throws BazarBalaDAOException {

		Result message = null;
		String sqlPhoneNo=null;
		List<PhoneAndEmail> phoneList=null;
		try {
			dataSource.getConnection().setAutoCommit(false);
			if (userType.equalsIgnoreCase("Customer")) {
				sqlPhoneNo = "SELECT PHONE_NO,EMAIL_ID FROM bazarbala.BAZAR_BALA_CUST_MST WHERE PHONE_NO=? OR EMAIL_ID=? ";
				phoneList = jdbcTemplate.query(sqlPhoneNo, new Object[] { phoneNumber, email },
						new RowMapper<PhoneAndEmail>() {
							public PhoneAndEmail mapRow(ResultSet resultSet, int rowNum) throws SQLException {
								PhoneAndEmail phoneAndEmail = new PhoneAndEmail();
								phoneAndEmail.setEmailId(resultSet.getString("EMAIL_ID"));
								phoneAndEmail.setPhoneNumber(resultSet.getString("PHONE_NO"));
								return phoneAndEmail;
							}

						});
			}
			if (userType.equalsIgnoreCase("Supplier")) {
				sqlPhoneNo = "SELECT PHONE_NO,EMAIL_ID FROM bazarbala.BAZAR_BALA_SUPP_MST WHERE PHONE_NO=? OR EMAIL_ID=?";
				phoneList = jdbcTemplate.query(sqlPhoneNo, new Object[] { phoneNumber, email },
						new RowMapper<PhoneAndEmail>() {
							public PhoneAndEmail mapRow(ResultSet resultSet, int rowNum) throws SQLException {
								PhoneAndEmail phoneAndEmail = new PhoneAndEmail();
								phoneAndEmail.setEmailId(resultSet.getString("EMAIL_ID"));
								phoneAndEmail.setPhoneNumber(resultSet.getString("PHONE_NO"));
								return phoneAndEmail;
							}

						});
			}
			
			if (phoneList.size() > 0) {
				PhoneAndEmail dbPhoneAndEmail=phoneList.get(0);
				String dbPhoneNumber=null;
				if(userType.equalsIgnoreCase("Customer")) {
				    dbPhoneNumber=dbPhoneAndEmail.getPhoneNumber()+"_Customer";
				}else {
					dbPhoneNumber=dbPhoneAndEmail.getPhoneNumber()+"_Supplier";
				}
				
		
				String tokenSql="INSERT INTO bazarbala.FORGET_PASSWORD (PHONE_NO,EMAIL_ID,TOKEN,TIME_STAMP) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE EMAIL_ID=?,TOKEN=?,TIME_STAMP=?";
				jdbcTemplate.update(tokenSql, new Object[] {dbPhoneNumber,dbPhoneAndEmail.getEmailId(),token,timeStamp,email,token,timeStamp});

				if(token != null && !token.isEmpty() ) {
					ForgetPassword forgetPassword = new ForgetPassword();
					forgetPassword.setEmailId(dbPhoneAndEmail.getEmailId());
					forgetPassword.setToken(token);
					forgetPassword.setTimeStamp(timeStamp);
					forgetPassword.setPhoneNo(dbPhoneAndEmail.getPhoneNumber());
					message = new Result(HttpStatus.OK,"Success", true,(ForgetPassword)forgetPassword);
				}else {
					message = new Result(HttpStatus.BAD_REQUEST, "Fail",false,null);
					message.setMessage(
							"Issue in setting your token number");
				}
				
				

			} else {
				
				message = new Result(HttpStatus.BAD_REQUEST, false);
				message.setMessage(
						"Your phone no. is not Present in our system in case please validate your provided input or Register");

			}

		} catch (SQLException sqlException) {
			logger.info("Error while customer {} for user Type {}", sqlException);
			throw new BazarBalaDAOException(sqlException.getMessage(), sqlException.getSQLState());
		} catch (Exception exception) {
			logger.info("Error while customer un_known exception {} for user Type {}", exception);
			throw new BazarBalaDAOException(exception.getMessage(), exception.getLocalizedMessage());
		}

		return message;
	}
	
	/**
	 * Validate the phone in database , every user should have unique phone no. 
	 * @param phoneNumber
	 * @param sql
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public Result isUpdatePassword(String password, String token, Long timeStamp) throws BazarBalaDAOException {

		Result message = null;
		String sqlForgetPassword = null;
		List<ForgetPassword> forgetPasswords = null;
		try {
			dataSource.getConnection().setAutoCommit(false);

			sqlForgetPassword = "SELECT * FROM bazarbala.FORGET_PASSWORD WHERE TOKEN=?";
			forgetPasswords = jdbcTemplate.query(sqlForgetPassword, new Object[] { token },
					new RowMapper<ForgetPassword>() {
						public ForgetPassword mapRow(ResultSet resultSet, int rowNum) throws SQLException {

							ForgetPassword forgetPassword = new ForgetPassword();

							forgetPassword.setTimeStamp(resultSet.getLong("TIME_STAMP"));
							forgetPassword.setPhoneNo(resultSet.getString("PHONE_NO"));

							return forgetPassword;
						}

					});

			if (forgetPasswords.size() > 0) {

				String updateSql = null;
				ForgetPassword forgetPassword = forgetPasswords.get(0);
				Long diffInMill = timeStamp - forgetPassword.getTimeStamp();
				int diffInMin = (int) (diffInMill / (60 * 1000));
				if (diffInMin <= 30) {
					String dbPhoneNumber = forgetPassword.getPhoneNo();
					int underscore = dbPhoneNumber.indexOf("_");
					String phoneNumber = dbPhoneNumber.substring(0, underscore);
					String userType = dbPhoneNumber.substring(underscore + 1, dbPhoneNumber.length());
					if (userType.equalsIgnoreCase("Customer")) {
						updateSql = "UPDATE bazarbala.BAZAR_BALA_CUST_MST SET PWD = ? WHERE PHONE_NO = ?";
					} else {
						updateSql = "UPDATE bazarbala.BAZAR_BALA_CUST_MST SET PWD = ? WHERE PHONE_NO = ?";
					}

					jdbcTemplate.update(updateSql, new Object[] { password, phoneNumber });

					message = new Result(HttpStatus.OK, "Success", true);
				} else {
					message = new Result(HttpStatus.BAD_REQUEST, "Token got expired please genrate toke again", false);
				}

			} else {

				message = new Result(HttpStatus.BAD_REQUEST, false);
				message.setMessage(
						"Your Token. is not Present in our system please check your mail and provide us the correct token");

			}

		} catch (SQLException sqlException) {
			logger.info("Error while customer {} for user Type {}", sqlException);
			throw new BazarBalaDAOException(sqlException.getMessage(), sqlException.getSQLState());
		} catch (Exception exception) {
			logger.info("Error while customer un_known exception {} for user Type {}", exception);
			throw new BazarBalaDAOException(exception.getMessage(), exception.getLocalizedMessage());
		}

		return message;
	}
	
	/**
	 * Fetching the data from database and mapping to the UserReposistory
	 * @param zipCode
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public List<UserReposistory> getSupplierrDetail(String zipCode)  throws BazarBalaDAOException{
		try {
		
		int extendedZip=Integer.parseInt(zipCode)+1;
		String extendedZipCode=String.valueOf(extendedZip);
		
		String sql = "SELECT * FROM bazarbala.BAZAR_BALA_SUPP_MST WHERE ZIPCODE=? OR ZIPCODE= ?";
		List<UserReposistory> actors = jdbcTemplate.query(
				sql,
				new Object[] {zipCode,extendedZipCode},
			    new RowMapper<UserReposistory>() {
			        public UserReposistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			        	UserReposistory userReposistory = new UserReposistory();
			        	userReposistory.setShopName(resultSet.getString("SHOP_NAME"));
			        	userReposistory.setShopId(resultSet.getString("SHOP_ID"));
			        	userReposistory.setShopAddress(resultSet.getString("ADDRESS"));
			        	userReposistory.setShopType(resultSet.getString("CATEGORY"));
			        	userReposistory.setZipCode(resultSet.getString("ZIPCODE"));
			            return userReposistory;
			        }

					
			    });
		 return actors;
		}  catch (Exception e) {
			logger.info("Error while retrive the Customer unknown exception {}" + e);
			throw new BazarBalaDAOException(e.getMessage(), e.getLocalizedMessage());
		}
	}
	
	/**
	 * create customer 
	 * @param createBusinessUserDTO
	 * @param address
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public Result creteCustomerUser(InstantShopCustomer createBusinessUserDTO,Address address) throws BazarBalaDAOException {

		
		Result message = null;
		String sqlPhopne="SELECT * FROM bazarbala.BAZAR_BALA_CUST_MST WHERE PHONE_NO=?";
		message=isValidPhonNumber(createBusinessUserDTO.getPhoneNnumber(),sqlPhopne);
		if(!message.isValid()) {
			return message;
		}
		
		try {
			dataSource.getConnection().setAutoCommit(false);
			int count = 0;

			String sql = "INSERT INTO " + SCHEMA_NAME
					+ ".BAZAR_BALA_CUST_MST(HOME_ID, CUSTOMER_ID,ADDRESS_ID,ADDRESS,ZIPCODE,FIRST_NAME,LAST_NAME,PHONE_NO,EMAIL_ID,PWD)\n"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?)";

			count = jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

					PreparedStatement ps = connection.prepareStatement(sql);

					ps.setLong(1, createBusinessUserDTO.getHomeId());
					ps.setString(2, String.valueOf(createBusinessUserDTO.getCustomerId()));
					ps.setString(3, String.valueOf(createBusinessUserDTO.getAddressId()));
					ps.setString(4, createBusinessUserDTO.getAddress());
					ps.setString(5, createBusinessUserDTO.getZipCode());
					ps.setString(6, createBusinessUserDTO.getFirstName());
					ps.setString(7, createBusinessUserDTO.getLastName());
					ps.setString(8, createBusinessUserDTO.getPhoneNnumber());
					ps.setString(9, createBusinessUserDTO.getEmailId());
					ps.setString(10, createBusinessUserDTO.getCustomerPwd());
					
					return ps;
				}
			});

			if (count > 0) {
				message=updateAddress(address,"Customer");
				if(message.isValid()) {
					message.setMessage("Company has been Successfully created with company ID : "
							+ createBusinessUserDTO.getCustomerId() + " .Pleae keep it secert and use as master admin.");
				}
			}else {
				message = new Result(HttpStatus.BAD_REQUEST,false);
				message.setMessage("Error While Creating a Company. Please contact to Admin. ");
				message.setValid(false);
			}

		} catch (SQLException sqlException) {
			logger.info("Error while customer {} for user Type {}", sqlException);
			throw new BazarBalaDAOException(sqlException.getMessage(), sqlException.getSQLState());
		} catch (Exception exception) {
			logger.info("Error while customer un_known exception {} for user Type {}",exception);
			throw new BazarBalaDAOException(exception.getMessage(), exception.getLocalizedMessage());
		}

		return message;
	}
	
	
	/**
	 * create customer 
	 * @param createBusinessUserDTO
	 * @param address
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public Result cretePasswordToken(String phoneNumber, String emailId, Long passwordToken, Long timeStamp,String userType)
			throws BazarBalaDAOException {
		
		return isValidAndUpdaePhonNumber(phoneNumber, emailId, String.valueOf(passwordToken), timeStamp,userType);

	}
	
	public Result updatePassword(String password, String token, Long timeStamp)
			throws BazarBalaDAOException {
		
		return isUpdatePassword(password, token,timeStamp);

	}
	
	
	/**
	 * For user setting its address to Address table
	 * @param address
	 * @param userType
	 * @throws BazarBalaDAOException
	 */
	private Result updateAddress(Address address,String userType) throws BazarBalaDAOException{
		Result message = null;
		
		try {
			dataSource.getConnection().setAutoCommit(false);
			int count = 0;

			String sql = "INSERT INTO " + SCHEMA_NAME
					+ ".BAZAR_ADDRESS_MST(ADDRESS_ID,ADDRESS,ZIPCODE,ID,USER_TYPE)\n"
					+ " VALUES (?,?,?,?,?)";

			count = jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

					PreparedStatement ps = connection.prepareStatement(sql);

					ps.setLong(1, address.getAddressId());
					ps.setString(2, address.getShopAddress());
					ps.setString(3, address.getZipCode());
					ps.setString(4, address.getId());
					ps.setString(5, userType);
					
					return ps;
				}
			});

			if (count > 0) {
				message = new Result(HttpStatus.OK,true);
				return message;
			}else {
				logger.info("Error while Address {} for user Type {}", userType);
				throw new BazarBalaDAOException("Address update got failed", "Reason Undefined");
			}
			

		} catch (SQLException sqlException) {
			logger.info("Error while Address {} for user Type {}", sqlException ,userType);
			throw new BazarBalaDAOException(sqlException.getMessage(), sqlException.getSQLState());
		} catch (Exception exception) {
			logger.info("Error while Address un_known exception {} for user Type {}",exception,userType);
			throw new BazarBalaDAOException(exception.getMessage(), exception.getLocalizedMessage());
		}
		
	}

}
