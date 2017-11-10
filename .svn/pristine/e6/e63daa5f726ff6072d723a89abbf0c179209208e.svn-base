package com.sogou.bizwork.cas.application.service;

import com.sogou.bizdev.dubhe.cache.annotation.CacheFlush;
import com.sogou.bizdev.dubhe.cache.annotation.CacheKey;
import com.sogou.bizdev.dubhe.cache.annotation.CacheKeyspace;
import com.sogou.bizdev.dubhe.cache.annotation.CacheValue;
import com.sogou.bizdev.dubhe.cache.annotation.Cacheable;
import com.sogou.bizwork.cas.utils.BizworkCredentials;
import com.sogou.bizwork.cas.utils.PCredentials;


/**
 * @author sixiaolin
 * @version 创建时间：2016-7-11 下午05:19:07
 * 
 */
public interface BizworkAuthorizationUserService {

    String LOGINUSER_CACHE_KEYSPACE = "/bizwork/loginUser/";

    @Cacheable(cacheName = "defaultCache", keyspace = LOGINUSER_CACHE_KEYSPACE, livetime = 168 * 3600)
    public BizworkCredentials queryCredentialsByTgt(@CacheKeyspace @CacheKey String tgtId);

    @CacheFlush(cacheName = "defaultCache", keyspaces = LOGINUSER_CACHE_KEYSPACE, livetime = 168 * 3600)
    public BizworkCredentials updateCredentialsByTgt(@CacheKeyspace @CacheKey String tgtId,
            @CacheValue BizworkCredentials credentials);

    @Cacheable(cacheName = "defaultCache", keyspace = LOGINUSER_CACHE_KEYSPACE, livetime = 168 * 3600)
    public PCredentials queryPCredentialsByTgt(@CacheKeyspace @CacheKey String tgtId);

    @CacheFlush(cacheName = "defaultCache", keyspaces = LOGINUSER_CACHE_KEYSPACE, livetime = 168 * 3600)
    public PCredentials updatePCredentialsByTgt(@CacheKeyspace @CacheKey String tgtId,
            @CacheValue PCredentials credentials);

    @Cacheable(cacheName = "defaultCache", keyspace = LOGINUSER_CACHE_KEYSPACE, livetime = 6 * 3600)
    public String querySidByServiceTicket(@CacheKeyspace @CacheKey String st);

    @CacheFlush(cacheName = "defaultCache", keyspaces = LOGINUSER_CACHE_KEYSPACE, livetime = 6 * 3600)
    public String updateSidByServiceTicket(@CacheKeyspace @CacheKey String st, @CacheValue String sid);

}
