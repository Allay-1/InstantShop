package com.bzbala.ad.bazarbala.order.repository;

import org.springframework.data.repository.CrudRepository;

import com.bzbala.ad.bazarbala.order.model.OrderHistoryItem;

public interface OrderHistoryItemRepository extends CrudRepository<OrderHistoryItem, Integer>{

}
