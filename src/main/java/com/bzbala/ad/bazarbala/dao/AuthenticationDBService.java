package com.bzbala.ad.bazarbala.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.bzbala.ad.bazarbala.dto.AuthUserUserDTO;
import com.bzbala.ad.bazarbala.order.helper.CreateOrUpdateOrderItems;
import com.bzbala.ad.bazarbala.order.model.CustomerOrder;
import com.bzbala.ad.bazarbala.order.model.Item;
import com.bzbala.ad.bazarbala.order.model.ItemRequest;
import com.bzbala.ad.bazarbala.order.model.ItemResponse;
import com.bzbala.ad.bazarbala.order.repository.CustomerOrderRepository;
import com.bzbala.ad.bazarbala.product.model.OrderStatus;
import com.bzbala.ad.bazarbala.util.BazarbalaUtil;

@Repository
public class AuthenticationDBService {
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String SCHEMA_NAME="bazarbala";
	
	@Autowired
    DataSource dataSource;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	CustomerOrderRepository customerOrderRepository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	CreateOrUpdateOrderItems createOrUpdateOrderItems;
	
	
	public boolean authenticateUserdbs(String phoneNo,String pwdToAuth){

		boolean isUpdated=false;
	    String sql = "SELECT COUNT(*) FROM "+SCHEMA_NAME+".BAZAR_BALA_SUPP_MST WHERE PHONE_NO=? AND PWD=?";
	    
	    isUpdated = jdbcTemplate.queryForObject(sql, new Object[] { phoneNo , pwdToAuth},Boolean.class);
	  
        return isUpdated;
	}

	
   /**
    * 
    * @param phoneNo
    * @param userType
    * @return
    */
	public PersonalInfo loadUserByUsernameDbs(String phoneNo, String userType, boolean isAuthenticated) {

		String sql = null;
		AuthUserUserDTOMapper authUserUserDTOMapper = new AuthUserUserDTOMapper();
		if (userType != null && !userType.isEmpty()
				&& (userType.equalsIgnoreCase("Supplier") || userType.equalsIgnoreCase("Admin"))) {
			sql = "SELECT PHONE_NO,PWD,SUPPLIER_ID,SHOP_ID FROM " + SCHEMA_NAME
					+ ".BAZAR_BALA_SUPP_MST WHERE PHONE_NO=?";

			jdbcTemplate.query(sql, new Object[] { phoneNo }, authUserUserDTOMapper);
		}

		if (userType != null && !userType.isEmpty() && (userType.equalsIgnoreCase("Customer")
				|| (authUserUserDTOMapper.getAuthUserUserDTO() == null && userType.equalsIgnoreCase("Admin")))) {
			sql = "SELECT PHONE_NO,PWD,CUSTOMER_ID,HOME_ID,ADDRESS,NEXTORDERNO FROM " + SCHEMA_NAME
					+ ".BAZAR_BALA_CUST_MST WHERE PHONE_NO=?";
			jdbcTemplate.query(sql, new Object[] { phoneNo }, authUserUserDTOMapper);
		}

		UserDetails userDetail = new User(authUserUserDTOMapper.getAuthUserUserDTO().getPhoneNo(),
				authUserUserDTOMapper.getAuthUserUserDTO().getPwdToAuth(), new ArrayList<>());

		PersonalInfo personalInfo = createPrersonalInfo(userDetail, authUserUserDTOMapper.getAuthUserUserDTO());
		if (isAuthenticated) {

			saveCurentOrderByUsername(personalInfo);
		}

		return personalInfo;
	}
	
	private PersonalInfo createPrersonalInfo(UserDetails userDetail,AuthUserUserDTO authUserUserDTO) {
		
		PersonalInfo personalInfo = new PersonalInfo();
		
		personalInfo.setUserDetail(userDetail);
		personalInfo.setAddress(authUserUserDTO.getAddress());
		personalInfo.setId(authUserUserDTO.getId());
		personalInfo.setNextOrderId(authUserUserDTO.getNextOrderId());
		personalInfo.setPersonalId(authUserUserDTO.getPersonalId());
		personalInfo.setPhoneNo(authUserUserDTO.getPhoneNo());
		personalInfo.setUserType(authUserUserDTO.getUserType());
		return personalInfo;
		
	}
	
	public void saveCurentOrderByUsername(PersonalInfo personalInfo) {

		if (personalInfo.getUserType() != null && !personalInfo.getUserType().isEmpty()
				&& personalInfo.getUserType().equalsIgnoreCase("Customer")) {

			saveOrder(personalInfo);
		}

	}
	/**
	 * 
	 * @param userType
	 * @param id
	 */
	public void saveOrder(PersonalInfo personalInfo) {
		CustomerOrder customerNewOrder = null;
		String bin = BazarbalaUtil.getBalaJiBIN(request, response);
		String customerBin = personalInfo.getId() + personalInfo.getNextOrderId().toString();
		Optional<CustomerOrder> customerOptionalOrder = null;
		if (bin != null && !bin.isEmpty()) {
			customerOptionalOrder = customerOrderRepository.findById(bin);
		} else {
			customerOptionalOrder = customerOrderRepository.findById(customerBin);
		}

		if (customerOptionalOrder != null && customerOptionalOrder.isPresent()) {
			CustomerOrder customerOrder = customerOptionalOrder.get();
			if (customerOrder != null) {
				String orderDate = customerOrder.getOrderDate();
				String lostUpdatedDate = customerOrder.getLastUpdatedDate();
				customerNewOrder = new CustomerOrder();
				customerNewOrder.setOrderId(customerBin);
				customerNewOrder.setOrderDate(orderDate);
				customerNewOrder.setLastUpdatedDate(lostUpdatedDate);
				customerNewOrder.setCustomerId(personalInfo.getPersonalId());
				customerNewOrder.setPhoneNumber(personalInfo.getPhoneNo());
				customerNewOrder.setShippingAddress(personalInfo.getAddress());
				// Need to use in future
				customerNewOrder.setTaxPercentange(10.0);
				ItemRequest itemRequest = createItemRequest(customerOrder);

				ItemResponse itemResponse = new ItemResponse();
				customerNewOrder.setOrderItems(
						createOrUpdateOrderItems.createOrUpdateOrderItem(customerNewOrder, itemRequest, itemResponse));
				BazarbalaUtil.processCustomerOrder(customerNewOrder);
				if(customerOrder.getOrderStatus() !=null) {
				customerNewOrder.setOrderStatus(OrderStatus.valueOf(customerOrder.getOrderStatus().toString()));
				}else {
					customerNewOrder.setOrderStatus(OrderStatus.valueOf("INCOMPLETE"));
				}
				customerOrderRepository.save(customerNewOrder);
				customerOrderRepository.deleteById(bin);
			}
		} else {

			customerNewOrder = new CustomerOrder();
			customerNewOrder.setOrderId(customerBin);
			customerNewOrder.setOrderDate(BazarbalaUtil.getCurrentDate().toString());
			customerNewOrder.setLastUpdatedDate(null);
			customerNewOrder.setCustomerId(personalInfo.getPersonalId());
			customerNewOrder.setPhoneNumber(personalInfo.getPhoneNo());
			customerNewOrder.setShippingAddress(personalInfo.getAddress());
			customerNewOrder.setTaxPercentange(10.0);
			customerNewOrder.setOrderStatus(OrderStatus.valueOf("INCOMPLETE"));
			BazarbalaUtil.removeCookie(response, "BIN", bin);
			BazarbalaUtil.isCookieAvailable(request, response, customerBin);

			customerOrderRepository.save(customerNewOrder);

		}

	}
	
	/**
	 * 
	 * @param customerOrder
	 * @return
	 */
	private ItemRequest createItemRequest(CustomerOrder customerOrder) {
		ItemRequest itemRequest = new ItemRequest();
		List<Item> items=null;
		if(customerOrder.getOrderItems() != null && !customerOrder.getOrderItems().isEmpty()) {
			items=customerOrder.getOrderItems().stream().map(orderItem->{
				Item item = new Item();
				item.setProductCode(orderItem.getProductCode());
				item.setQuantity(orderItem.getQuantity());
				item.setActionType("add");
				return item;
			}).collect(Collectors.toList());
		}
		itemRequest.setItems(items);
		return itemRequest;
	}
	
	
}
