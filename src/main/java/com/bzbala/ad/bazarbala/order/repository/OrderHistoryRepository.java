package com.bzbala.ad.bazarbala.order.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bzbala.ad.bazarbala.order.model.OrderHistory;

public interface OrderHistoryRepository extends CrudRepository<OrderHistory, String> {

	
	public List<OrderHistory> findAllByCustomerId(String customerId);
	

}


