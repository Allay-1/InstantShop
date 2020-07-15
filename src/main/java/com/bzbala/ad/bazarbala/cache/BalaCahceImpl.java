package com.bzbala.ad.bazarbala.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Repository;

@Repository
@EnableCaching
public class BalaCahceImpl implements BalaCache{
    
	@Cacheable(value = "order", key = "#key.toString()", unless = "#result=null")
	@Override
	public <T> T getCacheByKey(String key) {
		return null;
	}
    
	@Cacheable(value = "order", key = "#key.toString()", unless = "#result=null")
	@Override
	public <T> T putIntoCacheByKey(String key, T order) {
		return order;
	}
   
	@Cacheable(value = "order", key = "#key.toString()", unless = "#result=null")
	@Override
	public void deleteCacheByKey(String key) {

	}

}
