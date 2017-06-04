<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="de.fom.kp.persistence.Gender"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	Person List
	</jsp:attribute>
	<jsp:attribute name="headline">
	Nutzerkonto ${p.anrede} ${p.vorname} ${p.nachname}
	</jsp:attribute>
	<jsp:body>
		<table class="table table-hover">
		<thead>
			<tr>
				<th class="hidden-xs"><fmt:message key="i18n.gender"/></th>
				<th><fmt:message key="i18n.firstname"/></th>
				<th><fmt:message key="i18n.lastname"/></th>
				<th><fmt:message key="i18n.email"/></th>
				<th class="hidden-xs"><fmt:message key="i18n.birthday"/></th>
				<th class="hidden-xs"><fmt:message key="i18n.height"/></th>
				<th class="hidden-xs"><fmt:message key="i18n.newsletter"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personlist}" var="p">
				<tr>
					<td>${p.anrede}</td>
					<td>${p.vorname}</td>
					<td>${p.nachname}</td>
					<td><a href="<c:url value="/register.html?id=${p.id}"/>">${p.email}</a></td>
					<td class="hidden-xs"><fmt:formatDate value="${p.geburtsdatum}" pattern="${datepattern}"/></td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</jsp:body>
</my:base>