package com.sogou.bizwork.cas.application.service;


import java.util.List;

import com.sogou.bizdev.dubhe.cache.annotation.CacheEvict;
import com.sogou.bizdev.dubhe.cache.annotation.CacheFlush;
import com.sogou.bizdev.dubhe.cache.annotation.CacheKey;
import com.sogou.bizdev.dubhe.cache.annotation.CacheKeyspace;
import com.sogou.bizdev.dubhe.cache.annotation.CacheValue;
import com.sogou.bizdev.dubhe.cache.annotation.Cacheable;
import com.sogou.bizwork.cas.utils.CacheKeyspaceConstants;

import com.sogou.bizwork.cas.application.model.Application;

public interface BizworkApplicationService {
	
	@Cacheable(cacheName="defaultCache", keyspace=CacheKeyspaceConstants.ALL_APPLICATION)
	public  List<Application> selectAllApplications();
	
	@Cacheable(cacheName="defaultCache", keyspace=CacheKeyspaceConstants.APPLICATION_BY_APPID)
	public Application seleApplicationByAppId(@CacheKey String serviceId);
	
	@CacheEvict(cacheName="defaultCache",keyspaces=CacheKeyspaceConstants.ALL_APPLICATION)
	public void createApplication (@CacheKey (keyProperty="appId")  Application application);
	
	@Cacheable(cacheName="defaultCache", keyspace=CacheKeyspaceConstants.APPLICATION_BY_APPID)
    public int updateApplicationByAppId(@CacheKey(keyProperty="appId") @CacheValue Application record);
	
	 @CacheEvict(cacheName="defaultCache",keyspaces={CacheKeyspaceConstants.APPLICATION_BY_APPID,
			 CacheKeyspaceConstants.ALL_APPLICATION},evictKeynames={"",""})
 	public int deleteApplicationByAppId(Long appId);
	 
	public Application selectApplicationByPrimaryKey(Long appId);
}
