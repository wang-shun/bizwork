package com.sogou.bizwork.cas.principal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.ticket.ExpirationPolicy;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.TicketState;
import org.springframework.util.Assert;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午08:38:46
 * 类说明
 * 改写构造方法
 */
@MappedSuperclass
public abstract class BizworkAbstractTicket implements Ticket, TicketState{

	/** The ExpirationPolicy this ticket will be following. */
    @Lob
    @Column(name="EXPIRATION_POLICY", nullable=false)
    private ExpirationPolicy expirationPolicy;

    /** The unique identifier for this ticket. */
    @Id
    @Column(name="ID", nullable=false)
    private String id;

    /** The TicketGrantingTicket this is associated with. */
    @ManyToOne
    private BizworkTicketGrantingTicketImpl ticketGrantingTicket;

    /** The last time this ticket was used. */
    @Column(name="LAST_TIME_USED")
    private long lastTimeUsed;

    /** The previous last time this ticket was used. */
    @Column(name="PREVIOUS_LAST_TIME_USED")
    private long previousLastTimeUsed;

    /** The time the ticket was created. */
    @Column(name="CREATION_TIME")
    private long creationTime;

    /** The number of times this was used. */
    @Column(name="NUMBER_OF_TIMES_USED")
    private int countOfUses;
    
    protected BizworkAbstractTicket() {
        // nothing to do
    }

    /**
     * Constructs a new Ticket with a unique id, a possible parent Ticket (can
     * be null) and a specified Expiration Policy.
     * 
     * @param id the unique identifier for the ticket
     * @param ticket the parent TicketGrantingTicket
     * @param expirationPolicy the expiration policy for the ticket.
     * @throws IllegalArgumentException if the id or expiration policy is null.
     */
    public BizworkAbstractTicket(final String id, final BizworkTicketGrantingTicketImpl ticket,
        final ExpirationPolicy expirationPolicy) {
        Assert.notNull(expirationPolicy, "expirationPolicy cannot be null");
        Assert.notNull(id, "id cannot be null");

        this.id = id;
        this.creationTime = System.currentTimeMillis();
        this.lastTimeUsed = System.currentTimeMillis();
        this.expirationPolicy = expirationPolicy;
        this.ticketGrantingTicket = ticket;
    }

    public final String getId() {
        return this.id;
    }

    protected final void updateState() {
        this.previousLastTimeUsed = this.lastTimeUsed;
        this.lastTimeUsed = System.currentTimeMillis();
        this.countOfUses++;
    }

    public final int getCountOfUses() {
        return this.countOfUses;
    }

    public final long getCreationTime() {
        return this.creationTime;
    }

    public final TicketGrantingTicket getGrantingTicket() {
        return this.ticketGrantingTicket;
    }

    public final long getLastTimeUsed() {
        return this.lastTimeUsed;
    }

    public final long getPreviousTimeUsed() {
        return this.previousLastTimeUsed;
    }

    public final boolean isExpired() {
        return this.expirationPolicy.isExpired(this) || (getGrantingTicket() != null && getGrantingTicket().isExpired()) || isExpiredInternal();
    }

    protected boolean isExpiredInternal() {
        return false;
    }

    public final int hashCode() {
        return 34 ^ this.getId().hashCode();
    }

    public final String toString() {
        return this.id;
    }

}
