package com.sogou.bizwork.controller;

import org.jasig.cas.ticket.registry.TicketRegistry;
import org.springframework.beans.factory.annotation.Autowired;



public class AbstractSSOController {
	
	@Autowired
	public TicketRegistry bizworkTicketRegistry;

	public TicketRegistry getBizworkTicketRegistry() {
		return bizworkTicketRegistry;
	}

	public void setBizworkTicketRegistry(TicketRegistry bizworkTicketRegistry) {
		this.bizworkTicketRegistry = bizworkTicketRegistry;
	}
	


}
