package com.sogou.bizwork.cas.application.service;

import java.util.List;

import com.sogou.bizdev.dubhe.cache.annotation.CacheKey;
import com.sogou.bizdev.dubhe.cache.annotation.Cacheable;
import com.sogou.bizwork.cas.application.model.Attribute;
import com.sogou.bizwork.cas.utils.CacheKeyspaceConstants;

/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午05:19:07
 * 
 */
public interface AttributeService {
	
	@Cacheable(cacheName="defaultCache", keyspace=CacheKeyspaceConstants.ATTRIBUTE_BY_ID)
	public Attribute getAttributeById(@CacheKey Integer attributeId);
	
	@Cacheable(cacheName="defaultCache", keyspace=CacheKeyspaceConstants.ATTRIBUTE_BY_VALUE)
	public Attribute getAttributeByValue(@CacheKey String attributeValue);
	
	@Cacheable(cacheName="defaultCache", keyspace=CacheKeyspaceConstants.ALL_ATTRIBUTES)
	public List<Attribute> getAllAttribute();

}
