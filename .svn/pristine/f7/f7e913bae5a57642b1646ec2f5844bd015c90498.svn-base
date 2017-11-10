package com.sogou.bizwork.cas.application.service;

import java.util.List;

import com.sogou.bizdev.dubhe.cache.annotation.CacheEvict;
import com.sogou.bizdev.dubhe.cache.annotation.CacheEvictKey;
import com.sogou.bizdev.dubhe.cache.annotation.CacheKey;
import com.sogou.bizdev.dubhe.cache.annotation.CacheKeyspace;
import com.sogou.bizdev.dubhe.cache.annotation.Cacheable;
import com.sogou.bizwork.cas.application.model.AppAttribute;
import com.sogou.bizwork.cas.utils.CacheKeyspaceConstants;


/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午05:19:07
 * 
 */
public interface AppAttributeService {
	
	@Cacheable(cacheName="localCache", keyspace=CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPATTRID)
	public AppAttribute getAppAttributeById(@CacheKeyspace Integer appId, @CacheKey Integer appattributeId);
	
	@CacheEvict(cacheName="localCache", keyspaces={CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPATTRID, 
			CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPID}, evictKeynames={"appAttrId", "appId"})
	public void saveAppAttribute( @CacheKeyspace  @CacheEvictKey(name="appId") Integer appId, @CacheEvictKey(name="appAttrId", keyProperty="appattributeId") AppAttribute appAttribute);
	
	@CacheEvict(cacheName="localCache", keyspaces={CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPATTRID, 
			CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPID}, evictKeynames={"appAttrId", "appId"})
	public void updateAppAttribute( @CacheKeyspace  @CacheEvictKey(name="appId") Integer appId,  @CacheEvictKey(name="appAttrId", keyProperty="appattributeId") AppAttribute appAttribute);
	
	@CacheEvict(cacheName="localCache", keyspaces={CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPATTRID, 
			CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPID}, evictKeynames={"appAttrId", "appId"})
	public void deleteAppAttributeById( @CacheKeyspace  @CacheEvictKey(name="appId")Integer appId, @CacheEvictKey(name="appAttrId")  Integer appattributeId);
	
	//By AppId
	@Cacheable(cacheName="localCache", keyspace=CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPID)
	public List<AppAttribute> getAllAttributeByAppId( @CacheKeyspace @CacheKey Integer appId);
	
	@CacheEvict(cacheName="localCache", 
			keyspaces={CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPID,CacheKeyspaceConstants.APP_ATTRIBUTE_BY_APPATTRID}, 
			evictKeynames={"appId",""})
	public void deleteAppAttributeByAppId(@CacheKeyspace @CacheEvictKey(name="appId")  Integer appId);
	
}
