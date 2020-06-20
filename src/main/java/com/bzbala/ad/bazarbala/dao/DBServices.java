package com.bzbala.ad.bazarbala.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.bzbala.ad.bazarbala.dto.CreateBusinessUserDTO;
import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
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
	
	
	
	public boolean creteBusinessUser(CreateBusinessUserDTO createBusinessUserDTO) throws BazarBalaDAOException {

		boolean isAdded = false;

		try {
			dataSource.getConnection().setAutoCommit(false);
			int count = 0;

			String sql = "INSERT INTO " + SCHEMA_NAME
					+ ".BAZAR_BALA_MST(SHOP_ID, SHOP_NAME,SHOP_ADDRESS,ZIPCODE,PHONE_NO,"
					+ "SHOP_URL,SHOP_OWNER_EMAIL_ID,SHOP_OWNER_PWD,PAYMENT_PREFERANCE,SHOP_CATEGORY)\n"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?)";

			count = jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

					PreparedStatement ps = connection.prepareStatement(sql);

					ps.setLong(1, createBusinessUserDTO.getShopId());
					ps.setString(2, createBusinessUserDTO.getShopName());
					ps.setString(3, createBusinessUserDTO.getShopAddress());
					ps.setString(4, createBusinessUserDTO.getZipCode());
					ps.setString(5, createBusinessUserDTO.getPhoneNnumber());
					ps.setString(6, "http:localhost:8091/" + createBusinessUserDTO.getShopId());
					ps.setString(7, createBusinessUserDTO.getShopOwnerEmailId());
					ps.setString(8, createBusinessUserDTO.getShopOwnerPwd());
					ps.setString(9, createBusinessUserDTO.getPaymentPreference());
					ps.setString(10, createBusinessUserDTO.getShopCategory());
					return ps;
				}
			});

			if (count > 0) {
				isAdded = true;
			}

		} catch (SQLException se) {
			logger.info("Error while adding the Customer " + se);
			throw new BazarBalaDAOException(se.getMessage(), se.getSQLState());
		} catch (Exception e) {
			logger.info("Error while adding Customer " + e);
			throw new BazarBalaDAOException(e.getMessage(), e.getLocalizedMessage());
		}

		return isAdded;
	}
	
	/**
	 * Fetching the data from database and mapping to the UserReposistory
	 * @param zipCode
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public List<UserReposistory> getBusinessUser(String zipCode)  throws BazarBalaDAOException{
		try {
		
		int extendedZip=Integer.parseInt(zipCode)+1;
		String extendedZipCode=String.valueOf(extendedZip);
		
		String sql = "SELECT * FROM bazarbala.BAZAR_BALA_MST WHERE ZIPCODE=? OR ZIPCODE= ?";
		List<UserReposistory> actors = jdbcTemplate.query(
				sql,
				new Object[] {zipCode,extendedZipCode},
			    new RowMapper<UserReposistory>() {
			        public UserReposistory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			        	UserReposistory userReposistory = new UserReposistory();
			        	userReposistory.setShopName(resultSet.getString("SHOP_NAME"));
			        	userReposistory.setShopId(resultSet.getString("SHOP_ID"));
			        	userReposistory.setShopAddress(resultSet.getString("SHOP_ADDRESS"));
			        	userReposistory.setShopUrl(resultSet.getString("SHOP_URL"));
			        	userReposistory.setShopType(resultSet.getString("SHOP_CATEGORY"));
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

}
