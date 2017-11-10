package com.sogou.bizwork.client.example;

/**
 * Service to maintain TicketId to SessionId mapping.
 * 
 * @author liujianBJ7368
 * @since 2012-06-26
 */
public interface TicketIdToSessionIdMappingService {

	//@CacheFlush(keyspaces={CacheKeyspaceConstants.TICKET_TO_SESSION_ID_MAPPING}, livetime=3600*24*7)
	//public void setSessionIdByTicketId(@String ticketId, @CacheValue String sessionId);
	public void setSessionIdByTicketId( String ticketId,  String sessionId);
	
	//@Cacheable(keyspace=CacheKeyspaceConstants.TICKET_TO_SESSION_ID_MAPPING, useCachedObject=true, livetime=3600*24*7)
	public String getSessionIdByTicketId(String ticketId);
	
	//@CacheEvict(keyspaces={CacheKeyspaceConstants.TICKET_TO_SESSION_ID_MAPPING})
	public void deleteSessionIdByTicketId(String ticketId);
}
