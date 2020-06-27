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
import com.bzbala.ad.bazarbala.dto.InstantShopCustomer;
import com.bzbala.ad.bazarbala.dto.InstantShopSupplier;
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
