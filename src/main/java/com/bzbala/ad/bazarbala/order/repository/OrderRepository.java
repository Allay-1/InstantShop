package com.bzbala.ad.bazarbala.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.bzbala.ad.bazarbala.order.model.CustomerOrder;

public interface OrderRepository extends CrudRepository<CustomerOrder, Integer>{

}
