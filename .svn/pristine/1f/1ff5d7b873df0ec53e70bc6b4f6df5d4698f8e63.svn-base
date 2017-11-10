<%@ page session="false" contentType="text/xml; charset=UTF-8"%>
<%@ page import="java.net.*"%>
<%@ taglib

    prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib

    uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><cas:serviceResponse

    xmlns:cas='http://www.yale.edu/tp/cas'>

    <cas:authenticationSuccess>

        <cas:user>${fn:escapeXml(assertion.chainedAuthentications[fn:length(assertion.chainedAuthentications)-1].principal.id)}</cas:user>

       <c:if test="${not empty pgtIou}">

           <cas:proxyGrantingTicket>${pgtIou}</cas:proxyGrantingTicket>

       </c:if>
		<c:if test="${fn:length(assertion.chainedAuthentications[fn:length(assertion.chainedAuthentications)-1].principal.attributes) > 0}">  
		<cas:attributes>  
		<c:forEach var="attr" items="${assertion.chainedAuthentications[fn:length(assertion.chainedAuthentications)-1].principal.attributes}">
		<c:set var="key" value="${fn:escapeXml(attr.key)}" scope="request"></c:set>  
		<c:set var="value" value="${fn:escapeXml(attr.value)}" scope="request"></c:set>  
		<cas:${key}><%=URLEncoder.encode((String)request.getAttribute("value"),"UTF-8")%></cas:${key}>  
		</c:forEach>  
		</cas:attributes>  
		</c:if>  
		</cas:authenticationSuccess>

</cas:serviceResponse>