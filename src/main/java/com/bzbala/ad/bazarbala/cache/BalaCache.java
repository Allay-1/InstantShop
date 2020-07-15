package com.bzbala.ad.bazarbala.cache;

public interface BalaCache {
	
	public <T> T getCacheByKey(String key);
	
	public <T> T putIntoCacheByKey(String key, T order);
	
	public void deleteCacheByKey(String key);

}
