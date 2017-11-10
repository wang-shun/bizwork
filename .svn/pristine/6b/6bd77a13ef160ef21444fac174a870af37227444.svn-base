package com.sogou.bizwork.cas.principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;


import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.ExpirationPolicy;
import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午08:37:10
 * 类说明
 * 改写 grantServiceTicket 方法，维持services中，每个服务仅保存一份
 * 改写logout方法，logout成功时消除serviceSt这个map
 * 
 */

@Entity
@Table(name="TICKETGRANTINGTICKET")
public final class BizworkTicketGrantingTicketImpl extends BizworkAbstractTicket implements
TicketGrantingTicket{
	
    /** Unique Id for serialization. */
    private static final long serialVersionUID = -5197946718924168491L;

    private static final Logger LOG = LoggerFactory.getLogger(BizworkTicketGrantingTicketImpl.class);

    /** The authenticated object for which this ticket was generated for. */
    @Lob
    @Column(name="AUTHENTICATION", nullable=false)
    private Authentication authentication;

    /** Flag to enforce manual expiration. */
    @Column(name="EXPIRED", nullable=false)
    private Boolean expired = false;
    
    @Lob
    @Column(name="SERVICES_GRANTED_ACCESS_TO", nullable=false)
    private final ConcurrentHashMap<String,Service> services = new ConcurrentHashMap<String, Service>();
    
    private final ConcurrentHashMap<String,String> serviceSt = new ConcurrentHashMap<String, String>();
    
    public BizworkTicketGrantingTicketImpl() {
        // nothing to do
    }

    /**
     * Constructs a new TicketGrantingTicket.
     * 
     * @param id the id of the Ticket
     * @param ticketGrantingTicket the parent ticket
     * @param authentication the Authentication request for this ticket
     * @param policy the expiration policy for this ticket.
     * @throws IllegalArgumentException if the Authentication object is null
     */
    public BizworkTicketGrantingTicketImpl(final String id,
        final BizworkTicketGrantingTicketImpl ticketGrantingTicket,
        final Authentication authentication, final ExpirationPolicy policy) {
        super(id, ticketGrantingTicket, policy);
        Assert.notNull(authentication, "authentication cannot be null");
        this.authentication = authentication;
    }

    /**
     * Constructs a new TicketGrantingTicket without a parent
     * TicketGrantingTicket.
     * 
     * @param id the id of the Ticket
     * @param authentication the Authentication request for this ticket
     * @param policy the expiration policy for this ticket.
     */
    public BizworkTicketGrantingTicketImpl(final String id,
        final Authentication authentication, final ExpirationPolicy policy) {
        this(id, null, authentication, policy);
    }

    public Authentication getAuthentication() {
        return this.authentication;
    }

    public synchronized ServiceTicket grantServiceTicket(final String id,
        final Service service, final ExpirationPolicy expirationPolicy,
        final boolean credentialsProvided) {
        final ServiceTicket serviceTicket = new BizworkServiceTicketImpl(id, this,
            service, this.getCountOfUses() == 0 || credentialsProvided,
            expirationPolicy); 

        updateState();
        
        final List<Authentication> authentications = getChainedAuthentications();
        service.setPrincipal(authentications.get(authentications.size()-1).getPrincipal());
        
        if(serviceSt.containsKey(service.getId()))
        {
        	services.remove(serviceSt.get(service.getId()));
        }
        serviceSt.put(service.getId(), id);
        this.services.put(id, service);

        return serviceTicket;
    }
    
    private void logOutOfServices() {
        for (final Entry<String, Service> entry : this.services.entrySet()) {

            if (!entry.getValue().logOutOfService(entry.getKey())) {
                LOG.warn("Logout message not sent to [" + entry.getValue().getId() + "]; Continuing processing...");   
            }else {
				serviceSt.remove(entry.getValue().getId());
			}
        }
    }

    public boolean isRoot() {
        return this.getGrantingTicket() == null;
    }

    public synchronized void expire() {
        this.expired = true;
        logOutOfServices();
    }

    public boolean isExpiredInternal() {
        return this.expired;
    }

    public List<Authentication> getChainedAuthentications() {
        final List<Authentication> list = new ArrayList<Authentication>();

        if (this.getGrantingTicket() == null) {
            list.add(this.getAuthentication());
            return Collections.unmodifiableList(list);
        }

        list.add(this.getAuthentication());
        list.addAll(this.getGrantingTicket().getChainedAuthentications());

        return Collections.unmodifiableList(list);
    }
    
    public final boolean equals(final Object object) {
        if (object == null
            || !(object instanceof TicketGrantingTicket)) {
            return false;
        }

        final Ticket ticket = (Ticket) object;
        
        return ticket.getId().equals(this.getId());
    }
    
    public final ConcurrentHashMap<String,Service> getLoginServices()
    {
    	return this.services;
    }


}
