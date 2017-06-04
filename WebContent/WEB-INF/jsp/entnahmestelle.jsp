<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="de.fom.kp.persistence.Gender"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	Entnahmestelle
	</jsp:attribute>
	<jsp:attribute name="headline">
	Hier wird testweise eine Entnahmestelle angezeigt
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

			</tr>
		</thead>
		<tbody>
<%-- 			<c:forEach items="${entnahmestelle}" var="e"> --%>
				<tr>

					<td>${entnahmestelle.straße}</td>
					<td>${entnahmestelle.hausNr}</td>
					<td>${entnahmestelle.plz}</td>
					<td>${entnahmestelle.ort}</td>
<%-- 					<td>${p.name}</td> --%>
<%-- 					<td><a href="<c:url value="/register.html?id=${p.id}"/>">${p.email}</a></td> --%>
<%-- 					<td class="hidden-xs"><fmt:formatDate value="${p.birthday}" pattern="${datepattern}"/></td> --%>
<%-- 					<td class="hidden-xs"><fmt:formatNumber value="${p.height}"/></td> --%>
<!-- 					<td class="hidden-xs"> -->
<%-- 						<c:if test="${p.newsletter}"> --%>
<%-- 							<img src="<c:url value="/images/checkbox.png"/>"> --%>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${!p.newsletter}"> --%>
<%-- 							<img src="<c:url value="/images/checkbox_unchecked.png"/>"> --%>
<%-- 						</c:if> --%>
<!-- 					</td> -->
				</tr>
<%-- 			</c:forEach> --%>
		</tbody>
		</table>
	</jsp:body>
</my:base>