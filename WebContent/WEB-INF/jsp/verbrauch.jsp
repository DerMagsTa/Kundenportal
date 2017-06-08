<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="de.fom.kp.persistence.Gender"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	Test Verbrauch
	</jsp:attribute>
	<jsp:attribute name="headline">
	Verbrauch für Zähler 5
	</jsp:attribute>
	<jsp:body>
		<table class="table table-hover">
		<thead>
			<tr>
<%-- 				<th class="hidden-xs"><fmt:message key="i18n.gender"/></th> --%>
<%-- 				<th><fmt:message key="i18n.firstname"/></th> --%>
<%-- 				<th><fmt:message key="i18n.lastname"/></th> --%>
<%-- 				<th><fmt:message key="i18n.email"/></th> --%>
<%-- 				<th class="hidden-xs"><fmt:message key="i18n.birthday"/></th> --%>
				<th>ZählerId</th>
				<th>Datum von</th>
				<th>Datum bis</th>
				<th>Verbrauch</th>
				<th>Einheit</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${verbrauchsListe}" var="v">
				<tr>
					<td>${v.zId}</td>
					<td>${v.from}</td>
					<td>${v.to}</td>
					<td>${v.verbrauch}</td>
					<td>${v.unit}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</jsp:body>
</my:base>