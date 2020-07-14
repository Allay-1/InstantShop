package com.bzbala.ad.bazarbala.repository.Helper;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bzbala.ad.bazarbala.product.model.Price;

@Repository
public interface PriceRepository extends CrudRepository<Price,String> {

}
