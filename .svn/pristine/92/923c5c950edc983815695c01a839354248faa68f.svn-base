package com.sogou.bizwork.cas.principal;

import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.AbstractDistributedTicketRegistry;
import org.springframework.beans.factory.DisposableBean;

import net.spy.memcached.MemcachedClient;
import javax.validation.constraints.Min;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午09:15:52
 * 类说明
 * 
 * get 的时候 如果是 bizworkTGT则返回bizworkTGT类型
 */
public final class BizworkMemCacheTicketRegistry extends AbstractDistributedTicketRegistry implements DisposableBean{
	
	private final MemcachedClient client;
	
	@Min(0)
	private final int tgtTimeout;
	
	@Min(0)
	private final int stTimeout;
	
	private boolean synchronizeUpdatesToRegistry = false;
	
	/**
	 * Host names should be given in a list of the format: &lt;hostname&gt;:&lt;port&gt;
	 * 
	 * @param hostnames
	 * @param ticketGrantingTicketTimeOut
	 * @param serviceTicketTimeOut
	 */
	public BizworkMemCacheTicketRegistry(final String[] hostnames, final int ticketGrantingTicketTimeOut, final int serviceTicketTimeOut) {
		this.tgtTimeout = ticketGrantingTicketTimeOut;
		this.stTimeout = serviceTicketTimeOut;
		final List<InetSocketAddress> addresses = new ArrayList<InetSocketAddress>();
		
		for (final String hostname : hostnames) {
			String[] hostPort = hostname.split(":");
			addresses.add(new InetSocketAddress(hostPort[0], Integer.parseInt(hostPort[1])));
		}
		
		try {
			this.client = new MemcachedClient(addresses);
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}
	}

    /**
     * This alternative constructor takes time in milliseconds.  It has the timeout parameters first because, otherwise it would be almost
     * impossible to differentiate the two.
     * 
     * @param ticketGrantingTicketTimeOut
     * @param serviceTicketTimeOut
     * @param hostnames
     */
    public BizworkMemCacheTicketRegistry(final long ticketGrantingTicketTimeOut, final long serviceTicketTimeOut, final String[] hostnames) {
        this(hostnames, (int) (ticketGrantingTicketTimeOut / 1000), (int) (serviceTicketTimeOut / 1000));
    }
	
	private void handleSynchronousRequest(final Future f) {
	    try {
	        if (this.synchronizeUpdatesToRegistry) {
	            f.get();
	        }
	    } catch (final Exception e) {
	        // ignore these.
	    }
	}

	protected void updateTicket(final Ticket ticket) {
		if (ticket instanceof TicketGrantingTicket) {
			handleSynchronousRequest(this.client.replace(ticket.getId(), this.tgtTimeout, ticket));
		}
		
		if (ticket instanceof ServiceTicket) {
			handleSynchronousRequest(this.client.replace(ticket.getId(), this.stTimeout, ticket));
		}
	}

	public void addTicket(final Ticket ticket) {
		if (ticket instanceof TicketGrantingTicket) {
		    handleSynchronousRequest(this.client.add(ticket.getId(), this.tgtTimeout, ticket));
		}
		
		if (ticket instanceof ServiceTicket) {
		    handleSynchronousRequest(this.client.add(ticket.getId(), this.stTimeout, ticket));
		}
	}

	public boolean deleteTicket(final String ticketId) {
		Future<Boolean> f= this.client.delete(ticketId);
		try {
			return f.get().booleanValue();
		} catch (final Exception e) {
			log.error(e.getMessage(),e);
			return false;
		}
	}

	public Ticket getTicket(final String ticketId) {
		final Ticket t = (Ticket) this.client.get(ticketId);
		if (t == null) {
			return null;
		}
		if(t instanceof BizworkTicketGrantingTicketImpl)
		{
			return (BizworkTicketGrantingTicketImpl)t;
		}
		
		return getProxiedTicketInstance(t);
	}

	/**
	 * This operation is not supported.
	 * 
	 * @throws UnsupportedOperationException if you try and call this operation.
	 */
	public Collection<Ticket> getTickets() {
		throw new UnsupportedOperationException("GetTickets not supported.");
	}

	public void destroy() throws Exception {
		this.client.shutdown();
	}
	
	public void setSynchronizeUpdatesToRegistry(final boolean b) {
	    this.synchronizeUpdatesToRegistry = b;
	}

    @Override
    protected boolean needsCallback() {
        return true;
    }
}
