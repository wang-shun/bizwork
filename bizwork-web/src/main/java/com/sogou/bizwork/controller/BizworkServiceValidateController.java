package com.sogou.bizwork.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.WebApplicationService;
import org.jasig.cas.services.UnauthorizedServiceException;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.ticket.TicketValidationException;
import org.jasig.cas.validation.Assertion;
import org.jasig.cas.validation.Cas20ProtocolValidationSpecification;
import org.jasig.cas.validation.ValidationSpecification;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author 作者 E-mail:sixiaolin@sogou-inc.com
 * @version 创建时间：2016-7-19 下午07:35:51
 * 类说明
 */
@Controller
public class BizworkServiceValidateController extends AbstractController{
	
    private static final String DEFAULT_SERVICE_FAILURE_VIEW_NAME = "casServiceFailureView";

    private static final String DEFAULT_SERVICE_SUCCESS_VIEW_NAME = "casServiceSuccessView";
    /** Extracts parameters from Request object. */
    @NotNull
    @Autowired
    private ArgumentExtractor argumentExtractor;
    @NotNull
    @Autowired
    private CentralAuthenticationService bizworkAuthenticationService;
    /** The view to redirect to on a validation failure. */
    @NotNull
    private String failureView = DEFAULT_SERVICE_FAILURE_VIEW_NAME;
    
    /** The validation protocol we want to use. */
    @NotNull
    private Class<?> validationSpecificationClass = Cas20ProtocolValidationSpecification.class;
    /** The view to redirect to on a successful validation. */
    @NotNull
    private String successView = DEFAULT_SERVICE_SUCCESS_VIEW_NAME;
    
    private static final String MODEL_ASSERTION = "assertion";
	
    @RequestMapping(value = "/serviceValidate")	
    protected final ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final WebApplicationService service = this.argumentExtractor.extractService(request);
        final String serviceTicketId = service != null ? service.getArtifactId() : null;
        final String clientIp = request.getParameter("cip");
        if (service == null || serviceTicketId == null) {
            logger.info(String.format("Could not process request; Service: %s, Service Ticket Id: %s", service, serviceTicketId));
            return generateErrorView("INVALID_REQUEST", "INVALID_REQUEST", null);
        }
      
        try {

            final Assertion assertion = this.bizworkAuthenticationService.validateServiceTicket(serviceTicketId, service);
            
            final ValidationSpecification validationSpecification = this.getCommandClass();
            final ServletRequestDataBinder binder = new ServletRequestDataBinder(validationSpecification, "validationSpecification");
            initBinder(request, binder);
            binder.bind(request);

            if (!validationSpecification.isSatisfiedBy(assertion)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("ServiceTicket [" + serviceTicketId + "] does not satisfy validation specification.");
                }
                return generateErrorView("INVALID_TICKET", "INVALID_TICKET_SPEC", null);
            }

            onSuccessfulValidation(serviceTicketId, assertion);

            final ModelAndView success = new ModelAndView(this.successView);
            success.addObject(MODEL_ASSERTION, assertion);
            logger.info(String.format("Successfully validated service ticket: %s", serviceTicketId));
            return success;
        } catch (final TicketValidationException e) {
        	logger.warn("Code:"+e.getCode()+" serviceTicketId:"+serviceTicketId+" service:"+service.getId()+" Message:"+e.getMessage());
            return generateErrorView(e.getCode(), e.getCode(), new Object[] {serviceTicketId, e.getOriginalService().getId(), service.getId()});
        } catch (final TicketException te) {
        	logger.warn("Code:"+te.getCode()+" serviceTicketId:"+serviceTicketId+" service:"+service.getId()+" Message:"+te.getMessage());
            return generateErrorView(te.getCode(), te.getCode(),
                new Object[] {serviceTicketId});
        } catch (final UnauthorizedServiceException e) {
        	logger.warn("serviceTicketId:"+serviceTicketId+" service:"+service.getId()+" Message:"+e.getMessage());
            return generateErrorView(e.getMessage(), e.getMessage(), null);
        }
    }
    
    private ModelAndView generateErrorView(final String code, final String description, final Object[] args) {
        final ModelAndView modelAndView = new ModelAndView(this.failureView);
        final String convertedDescription = getMessageSourceAccessor().getMessage(description, args, description);
        modelAndView.addObject("code", code);
        modelAndView.addObject("description", convertedDescription);

        return modelAndView;
    }
    
    private ValidationSpecification getCommandClass() {
        try {
            return (ValidationSpecification) this.validationSpecificationClass.newInstance();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        binder.setRequiredFields( new String[]{"renew"});
    }
    
    protected void onSuccessfulValidation(final String serviceTicketId, final Assertion assertion) {
        // template method with nothing to do.
    }

	public ArgumentExtractor getArgumentExtractor() {
		return argumentExtractor;
	}

	public void setArgumentExtractor(ArgumentExtractor argumentExtractor) {
		this.argumentExtractor = argumentExtractor;
	}

	public CentralAuthenticationService getBizworkAuthenticationService() {
		return bizworkAuthenticationService;
	}

	public void setBizworkAuthenticationService(
			CentralAuthenticationService bizworkAuthenticationService) {
		this.bizworkAuthenticationService = bizworkAuthenticationService;
	}

	public String getFailureView() {
		return failureView;
	}

	public void setFailureView(String failureView) {
		this.failureView = failureView;
	}

	public Class<?> getValidationSpecificationClass() {
		return validationSpecificationClass;
	}

	public void setValidationSpecificationClass(
			Class<?> validationSpecificationClass) {
		this.validationSpecificationClass = validationSpecificationClass;
	}

	public String getSuccessView() {
		return successView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}
}
