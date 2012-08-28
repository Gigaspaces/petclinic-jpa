<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<img src="<spring:url value="/static/images/animals.png" htmlEscape="true" />" align="right" style="position:relative;right:30px;">
<h2><fmt:message key="welcome"/></h2>

<ul>
  <li><a href="<spring:url value="/owners/search" htmlEscape="true" />">Find owner</a></li>
  <li><a href="<spring:url value="/vets" htmlEscape="true" />">Display all veterinarians</a></li>
  <li><a href="<spring:url value="http://www.gigaspaces.com/wiki/display/XAP8/Introduction+to+XAP+JPA" htmlEscape="true" />" target="_new">Tutorial</a></li>
  <li><a href="<spring:url value="/createDummyData" htmlEscape="true" />">Create Dummy Data</a></li>
  <c:if test="${dummyDataCreated != null}">
    <span>Dummy Data Created</span>
  </c:if>
</ul>

<p>&nbsp;</p>
<p>&nbsp;</p>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
