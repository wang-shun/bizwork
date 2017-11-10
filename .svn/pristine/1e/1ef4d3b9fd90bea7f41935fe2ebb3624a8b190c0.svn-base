package com.sogou.bizwork.cas.application.service;

import com.sogou.bizdev.dubhe.cache.annotation.CacheEvict;
import com.sogou.bizdev.dubhe.cache.annotation.CacheFlush;
import com.sogou.bizdev.dubhe.cache.annotation.CacheKey;
import com.sogou.bizdev.dubhe.cache.annotation.CacheKeyspace;
import com.sogou.bizdev.dubhe.cache.annotation.CacheValue;
import com.sogou.bizdev.dubhe.cache.annotation.Cacheable;

/**
 * 
 * @author sixiaolin
 * 
 * @see  check whether user has login a service and can visit another service 
 */
public interface BizworkAuthorizationApplicationService {
	
	String LOGONSERVICE_CACHE_KEYSPACE = "/bizwork/logonservicetype/";
	
	@Cacheable(cacheName="defaultCache", keyspace=LOGONSERVICE_CACHE_KEYSPACE,  livetime=6*3600)
	public Short queryLogonServiceTypeByTgt(@CacheKeyspace @CacheKey String tgtId);
	
	@CacheFlush(cacheName="defaultCache", keyspaces=LOGONSERVICE_CACHE_KEYSPACE , livetime=6*3600)
	public Short updateLogonServiceTypeByTgt(@CacheKeyspace @CacheKey String tgtId, @CacheValue Short userSource);
	
	@CacheEvict(cacheName="defaultCache", keyspaces=LOGONSERVICE_CACHE_KEYSPACE)
	public void clearLogonServiceTypeByTgt(@CacheKeyspace @CacheKey String tgtId);
}
