<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="MessageResources" scope="session" />
<%@ attribute name="field" required="true" rtexprvalue="false" %>
<%@ attribute name="errorlist" required="true" rtexprvalue="true" type="java.util.List" %>
<c:forEach items="${errorlist}" var="e">
	<c:if test="${e.field eq field}">
		<span class="error"><jsp:doBody/><fmt:message key= "${e.message}"/></span>
	</c:if>
</c:forEach>