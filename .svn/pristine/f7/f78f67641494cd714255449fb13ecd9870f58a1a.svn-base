package com.sogou.bizwork.cas.authentication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.Authentication;
import org.jasig.cas.authentication.AuthenticationManager;
import org.jasig.cas.authentication.MutableAuthentication;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.PersistentIdGenerator;
import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.ShibbolethCompatiblePersistentIdGenerator;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.services.ServicesManager;
import org.jasig.cas.services.UnauthorizedServiceException;
import org.jasig.cas.services.UnauthorizedSsoServiceException;
import org.jasig.cas.ticket.ExpirationPolicy;
import org.jasig.cas.ticket.InvalidTicketException;
import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.TicketCreationException;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.TicketGrantingTicketImpl;
import org.jasig.cas.ticket.TicketValidationException;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.jasig.cas.validation.Assertion;
import org.jasig.cas.validation.ImmutableAssertionImpl;
import org.perf4j.aop.Profiled;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.inspektr.audit.annotation.Audit;
import com.sogou.bizwork.cas.application.service.BizworkApplicationService;
import com.sogou.bizwork.cas.constants.AuthenticationLogHelper;
import com.sogou.bizwork.cas.constants.SSOConstantsCode;
import com.sogou.bizwork.cas.constants.SSOLogConstantsCode;
import com.sogou.bizwork.cas.constants.UUCConstants;
import com.sogou.bizwork.cas.principal.BizworkTicketGrantingTicketImpl;
import com.sogou.bizwork.cas.web.support.BizworkAttributes;


/**
 * Authentication Service for both SSO and delegation.
 * 
 * @author liujianBJ7368
 * @since 2012-05-18
 */
public class BizworkAuthenticationService implements
		CentralAuthenticationService,
		SSOConstantsCode, SSOLogConstantsCode, UUCConstants {

	private CentralAuthenticationService centralAuthenticationService;

	@NotNull
	private TicketRegistry bizworkTicketRegistry;
	@NotNull
	private TicketRegistry ticketRegistry;
	@NotNull
	private AuthenticationManager authenticationManager;
	@NotNull
	private UniqueTicketIdGenerator ticketGrantingTicketUniqueTicketIdGenerator;
	@NotNull
	private Map<String, UniqueTicketIdGenerator> uniqueTicketIdGeneratorsForService;
	@NotNull
	private ExpirationPolicy ticketGrantingTicketExpirationPolicy;
	@NotNull
	private ExpirationPolicy serviceTicketExpirationPolicy;
	@NotNull
	private ServicesManager servicesManager;
	@NotNull
	private PersistentIdGenerator persistentIdGenerator = new ShibbolethCompatiblePersistentIdGenerator();

	@Resource
	private BizworkAttributes bizworkAttributes;
	
	private BizworkApplicationService bizworkApplicationService;
	
	private static final Logger logger = Logger
			.getLogger(BizworkAuthenticationService.class);


	public String grantServiceTicket(String ticketGrantingTicketId,
			Service service) throws TicketException {
		return grantServiceTicket(ticketGrantingTicketId, service, null);
	}


	@Audit(action = "SERVICE_TICKET_VALIDATE", actionResolverName = "VALIDATE_SERVICE_TICKET_RESOLVER", resourceResolverName = "VALIDATE_SERVICE_TICKET_RESOURCE_RESOLVER")
	@Transactional(readOnly = false)
	public Assertion validateServiceTicket(String serviceTicketId,
			Service service) throws TicketException {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
				
		if (null != serviceTicketId) {
			Assert.notNull(serviceTicketId,	"serviceTicketId cannot be null");
			Assert.notNull(service, "service cannot be null");
			ServiceTicket serviceTicket=null;
			try {
				serviceTicket = (ServiceTicket) this.ticketRegistry
							.getTicket(serviceTicketId, ServiceTicket.class);
				} catch (Exception e1) {
					logger.warn("Get ticket from serviceTicketRegistry Error, ticket="+serviceTicketId +" "+ e1.getMessage());
				}
				if (serviceTicket == null) {
					AuthenticationLogHelper.serviceAuthenticationEnd(
							SERVICE_LOGIN_FAILED, SERVICE_ST_NOT_EXIST,
							service.getId(),
							request.getSession().getId(), null, null, null,
							"ServiceTicket [" + serviceTicketId
									+ "] does not exist");
					throw new InvalidTicketException();
				}
				String userName = null;
				try {
					final RegisteredService registeredService = this.servicesManager
							.findServiceBy(service);
					final int authenticationChainSize = serviceTicket
							.getGrantingTicket().getChainedAuthentications()
							.size();
					final Authentication authentication = serviceTicket
							.getGrantingTicket().getChainedAuthentications()
							.get(authenticationChainSize - 1);
					final Principal principal = authentication.getPrincipal();
					String principalId = null;
					if (null != principal.getId()) {
						principalId = principal.getId();
						userName = principalId;
					} else if (registeredService.isAnonymousAccess()) {
						principalId = this.persistentIdGenerator.generate(
								principal, serviceTicket.getService());
					} else {
						AuthenticationLogHelper
								.serviceAuthenticationEnd(
										SERVICE_LOGIN_FAILED,
										SERVICE_ST_NOT_EXIST, 
										service.getId(), request.getSession()
												.getId(), null, null, null,
										"Principal Id is null and service do not allow anonymous access");
						throw new InvalidTicketException();
					}

					if (logger.isDebugEnabled()) {
						logger.debug("Service reterive from registry "
								+ registeredService != null ? registeredService
								.getServiceId() : null + " using Service "
								+ service.getId());
					}
					if (registeredService == null
							|| !registeredService.isEnabled()) {
						logger.info("validate ST: service:" + registeredService != null ? registeredService
								.getServiceId() : null + "service.isEnabled"
								+ registeredService != null ? registeredService
								.isEnabled() : null);
						AuthenticationLogHelper.serviceAuthenticationEnd(
								SERVICE_LOGIN_FAILED,
								SERVICE_NOT_ENABLED,
								service.getId(),
								request.getSession().getId(), null,
								principalId, null, "Service " + service
										+ "is not enabled");
						throw new UnauthorizedServiceException(
								"Service not allowed to validate tickets.");
					}
 
					final Authentication authToUse;
					synchronized (serviceTicket) {
						if (serviceTicket.isExpired()) {
							logger.info("ServiceTicket [" + serviceTicketId
									+ "] has expired.");
							AuthenticationLogHelper.serviceAuthenticationEnd(
									SERVICE_LOGIN_FAILED,
									SERVICE_ST_EXPIRED, 
									service.getId(), request.getSession()
											.getId(), null, principalId, null,
									"ServiceTicket [" + serviceTicketId
											+ "] has expired");
							throw new InvalidTicketException();
						}
						if (!serviceTicket.isValidFor(service)) {
							logger.error("ServiceTicket [" + serviceTicketId
									+ "] with service ["
									+ serviceTicket.getService().getId()
									+ " does not match supplied service ["
									+ service + "]");
							AuthenticationLogHelper
									.serviceAuthenticationEnd(
											SERVICE_LOGIN_FAILED,
											SERVICE_ST_INVALID,
											service.getId(),
											request.getSession().getId(),
											null,
											principalId,
											null,
											"ServiceTicket ["
													+ serviceTicketId
													+ "] with service ["
													+ serviceTicket
															.getService()
															.getId()
													+ " does not match supplied service ["
													+ service + "]");
							throw new TicketValidationException(
									serviceTicket.getService());
						}
					}

					
						Map<String, Object> full_attr = new HashMap<String, Object>();
						full_attr = bizworkAttributes.getAllAttributes(service.getId(), principalId, serviceTicket.getGrantingTicket().getId());
						final Principal modifyPrincipal = new SimplePrincipal(
								principalId, full_attr);
						final MutableAuthentication modifyAuthentication = new MutableAuthentication(
								modifyPrincipal,
								authentication.getAuthenticatedDate());
						modifyAuthentication.getAttributes().putAll(full_attr);
						modifyAuthentication.getAuthenticatedDate()
								.setTime(
										authentication.getAuthenticatedDate()
												.getTime());
						Integer userId = (Integer) full_attr.get(USER_ID);
						
						AuthenticationLogHelper.serviceAuthenticationEnd(
								SERVICE_LOGIN_SUCCESS,
								SERVICE_LOGIN_SUCCESS,
								service.getId(),
								request.getSession().getId(),
								userId != null ? userId : -1L,
								principalId,
								"0",
								"User: "
										+ principalId
										+ " has login "
										+ service.getId() + " Successfully");
						authToUse = modifyAuthentication;
					

					final List<Authentication> authentications = new ArrayList<Authentication>();

					for (int i = 0; i < authenticationChainSize - 1; i++) {
						authentications.add(serviceTicket.getGrantingTicket()
								.getChainedAuthentications().get(i));
					}
					authentications.add(authToUse);
						 
					return new ImmutableAssertionImpl(authentications,
							serviceTicket.getService(),
							serviceTicket.isFromNewLogin());
				} catch (DataAccessException e) {
					AuthenticationLogHelper.serviceAuthenticationEnd(
							SERVICE_LOGIN_FAILED,
							SERVICE_DATAACCESS_EXCEPTION,
							service.getId(),
							request.getSession().getId(), null, null, null,
							"Data Access Occur Exception ");
				}catch (Exception e) {
					AuthenticationLogHelper.serviceAuthenticationEnd(
							SERVICE_LOGIN_FAILED,
							SERVICE_DATAACCESS_EXCEPTION,
							service.getId(),
							request.getSession().getId(), null, null, null,
							e.getMessage());
				}
				finally {
					if (serviceTicket.isExpired()) {
						this.ticketRegistry
								.deleteTicket(serviceTicketId);
					}
				}

			
		}
		AuthenticationLogHelper.serviceAuthenticationEnd(
				SERVICE_LOGIN_FAILED,
				SERVICE_ST_NOT_EXIST, 
				service.getId(), request.getSession().getId(), null, null, null,
				"service ticket id is null");
		throw new TicketValidationException(service);

	}
	
    /**
     * @throws IllegalArgumentException if the credentials are null.
     */
    @Audit(
        action="TICKET_GRANTING_TICKET",
        actionResolverName="CREATE_TICKET_GRANTING_TICKET_RESOLVER",
        resourceResolverName="CREATE_TICKET_GRANTING_TICKET_RESOURCE_RESOLVER")
    @Profiled(tag = "CREATE_TICKET_GRANTING_TICKET", logFailuresSeparately = false)
    @Transactional(readOnly = false)
    public String createTicketGrantingTicket(final Credentials credentials) throws TicketCreationException {
        Assert.notNull(credentials, "credentials cannot be null");

        try {
            final Authentication authentication = this.authenticationManager
                .authenticate(credentials);

            final TicketGrantingTicket ticketGrantingTicket = new BizworkTicketGrantingTicketImpl(
                this.ticketGrantingTicketUniqueTicketIdGenerator
                    .getNewTicketId(TicketGrantingTicket.PREFIX),
                authentication, this.ticketGrantingTicketExpirationPolicy);
            this.ticketRegistry.addTicket(ticketGrantingTicket);
            
            return ticketGrantingTicket.getId();
        } catch (final AuthenticationException e) {
            throw new TicketCreationException(e);
        }
    }
    
    @Audit(
            action="SERVICE_TICKET",
            actionResolverName="GRANT_SERVICE_TICKET_RESOLVER",
            resourceResolverName="GRANT_SERVICE_TICKET_RESOURCE_RESOLVER")
        @Profiled(tag="GRANT_SERVICE_TICKET", logFailuresSeparately = false)
        @Transactional(readOnly = false)
        public String grantServiceTicket(final String ticketGrantingTicketId, final Service service, final Credentials credentials) throws TicketException {

            Assert.notNull(ticketGrantingTicketId, "ticketGrantingticketId cannot be null");
            Assert.notNull(service, "service cannot be null");

            final TicketGrantingTicket ticketGrantingTicket;
            ticketGrantingTicket = (TicketGrantingTicket) this.ticketRegistry.getTicket(ticketGrantingTicketId, TicketGrantingTicket.class);
            if (ticketGrantingTicket == null) {
                throw new InvalidTicketException();
            }

            synchronized (ticketGrantingTicket) {
                if (ticketGrantingTicket.isExpired()) {
                    this.ticketRegistry.deleteTicket(ticketGrantingTicketId);
                    throw new InvalidTicketException();
                }
            }

            final RegisteredService registeredService = this.servicesManager 
                .findServiceBy(service);

            if (registeredService == null || !registeredService.isEnabled()) {
            	logger.warn("ServiceManagement: Unauthorized Service Access. Service [" + service.getId() + "] not found in Service Registry.");
                throw new UnauthorizedServiceException();
            }

            if (!registeredService.isSsoEnabled() && credentials == null
                && ticketGrantingTicket.getCountOfUses() > 0) {
            	logger.warn("ServiceManagement: Service Not Allowed to use SSO.  Service [" + service.getId() + "]");
                throw new UnauthorizedSsoServiceException();
            }

            if (credentials != null) {
                try {
                    final Authentication authentication = this.authenticationManager
                        .authenticate(credentials);
                    final Authentication originalAuthentication = ticketGrantingTicket.getAuthentication();

                    if (!(authentication.getPrincipal().equals(originalAuthentication.getPrincipal()) && authentication.getAttributes().equals(originalAuthentication.getAttributes()))) {
                        throw new TicketCreationException();
                    }
                } catch (final AuthenticationException e) {
                    throw new TicketCreationException(e);
                }
            }

            // this code is a bit brittle by depending on the class name.  Future versions (i.e. CAS4 will know inherently how to identify themselves)
            final UniqueTicketIdGenerator serviceTicketUniqueTicketIdGenerator = this.uniqueTicketIdGeneratorsForService
                .get(service.getClass().getName());

            final ServiceTicket serviceTicket = ticketGrantingTicket
                .grantServiceTicket(serviceTicketUniqueTicketIdGenerator
                    .getNewTicketId(ServiceTicket.PREFIX), service,
                    this.serviceTicketExpirationPolicy, credentials != null);
            
            this.ticketRegistry.addTicket(serviceTicket);
            

            if (logger.isInfoEnabled()) {
                final List<Authentication> authentications = serviceTicket.getGrantingTicket().getChainedAuthentications();
                final String formatString = "Granted %s ticket [%s] for service [%s] for user [%s]";
                final String type;
                final String principalId = authentications.get(authentications.size()-1).getPrincipal().getId();

                if (authentications.size() == 1) {
                    type = "service";

                } else {
                    type = "proxy";
                }

                logger.info(String.format(formatString, type, serviceTicket.getId(), service.getId(), principalId));
            }

            return serviceTicket.getId();
        }

	public void destroyTicketGrantingTicket(String ticketGrantingTicketId) {
		centralAuthenticationService
				.destroyTicketGrantingTicket(ticketGrantingTicketId);
	}

	public String delegateTicketGrantingTicket(String serviceTicketId,
			Credentials credentials) throws TicketException {
		return centralAuthenticationService.delegateTicketGrantingTicket(
				serviceTicketId, credentials);
	}

	public void setCentralAuthenticationService(
			CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}

	public CentralAuthenticationService getCentralAuthenticationService() {
		return centralAuthenticationService;
	}

	public TicketRegistry getTicketRegistry() {
		return ticketRegistry;
	}

	public void setTicketRegistry(TicketRegistry ticketRegistry) {
		this.ticketRegistry = ticketRegistry;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public TicketRegistry getBizworkTicketRegistry() {
		return bizworkTicketRegistry;
	}


	public void setBizworkTicketRegistry(TicketRegistry bizworkTicketRegistry) {
		this.bizworkTicketRegistry = bizworkTicketRegistry;
	}


	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public Map<String, UniqueTicketIdGenerator> getUniqueTicketIdGeneratorsForService() {
		return uniqueTicketIdGeneratorsForService;
	}

	public void setUniqueTicketIdGeneratorsForService(
			Map<String, UniqueTicketIdGenerator> uniqueTicketIdGeneratorsForService) {
		this.uniqueTicketIdGeneratorsForService = uniqueTicketIdGeneratorsForService;
	}

	public ExpirationPolicy getServiceTicketExpirationPolicy() {
		return serviceTicketExpirationPolicy;
	}

	public void setServiceTicketExpirationPolicy(
			ExpirationPolicy serviceTicketExpirationPolicy) {
		this.serviceTicketExpirationPolicy = serviceTicketExpirationPolicy;
	}

	public ServicesManager getServicesManager() {
		return servicesManager;
	}

	public void setServicesManager(ServicesManager servicesManager) {
		this.servicesManager = servicesManager;
	}

	public UniqueTicketIdGenerator getTicketGrantingTicketUniqueTicketIdGenerator() {
		return ticketGrantingTicketUniqueTicketIdGenerator;
	}

	public void setTicketGrantingTicketUniqueTicketIdGenerator(
			UniqueTicketIdGenerator ticketGrantingTicketUniqueTicketIdGenerator) {
		this.ticketGrantingTicketUniqueTicketIdGenerator = ticketGrantingTicketUniqueTicketIdGenerator;
	}

	public ExpirationPolicy getTicketGrantingTicketExpirationPolicy() {
		return ticketGrantingTicketExpirationPolicy;
	}

	public void setTicketGrantingTicketExpirationPolicy(
			ExpirationPolicy ticketGrantingTicketExpirationPolicy) {
		this.ticketGrantingTicketExpirationPolicy = ticketGrantingTicketExpirationPolicy;
	}

	public PersistentIdGenerator getPersistentIdGenerator() {
		return persistentIdGenerator;
	}

	public void setPersistentIdGenerator(
			PersistentIdGenerator persistentIdGenerator) {
		this.persistentIdGenerator = persistentIdGenerator;
	}


	public BizworkAttributes getBizworkAttributes() {
		return bizworkAttributes;
	}


	public void setBizworkAttributes(BizworkAttributes bizworkAttributes) {
		this.bizworkAttributes = bizworkAttributes;
	}


	public BizworkApplicationService getBizworkApplicationService() {
		return bizworkApplicationService;
	}


	public void setBizworkApplicationService(
			BizworkApplicationService bizworkApplicationService) {
		this.bizworkApplicationService = bizworkApplicationService;
	}
}
