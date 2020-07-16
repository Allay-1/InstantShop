package com.bzbala.ad.bazarbala.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.bzbala.ad.bazarbala.order.model.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer>{
	

}
