package com.sogou.bizwork.cas.utils;

/**
 * CacheKeyspace.
 * 
 * @author liujianbj7368
 * @since 2012-07-31
 */
public interface CacheKeyspaceConstants {
	
	//Attribute Keyspace
	public String ATTRIBUTE_BY_ID = ".Bizwork.AttributeById.";
	
	public String ATTRIBUTE_BY_VALUE = ".Bizwork.AttributeByValue.";
	
	public String ALL_ATTRIBUTES = ".Bizwork.AllAttribute.";
	
	//AppAttribute Keyspace
	public String APP_ATTRIBUTE_BY_APPATTRID = ".Bizwork.AppAttributeByAppAttrId.";
	
	public String APP_ATTRIBUTE_BY_APPID = ".Bizwork.AppAttributesByAppId.";
	
	//Application Keyspace
	public String ALL_APPLICATION = "Bizwork.AllRegisteredService.";
	
	public String APPLICATION_BY_APPID = "Bizwork.ServiceByServiceId.";
}
