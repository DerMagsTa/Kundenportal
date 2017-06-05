<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
				<th><fmt:message key="i18n.ID"/></th>
				<th><fmt:message key="i18n.Person"/></th>
				<th><fmt:message key="i18n.Straﬂe"/></th>
				<th><fmt:message key="i18n.Haus-Nr"/></th>
				<th><fmt:message key="i18n.PLZ"/></th>
				<th><fmt:message key="i18n.Ort"/></th>
				<th class="hidden-xs"><fmt:message key="i18n.Land"/></th>
				<th class="hidden-xs"><fmt:message key="i18n.Hinweis"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entnahmestelle}" var="e">
				<tr>
					<td>${e.id}</td>
					<td>${e.personId}</td>
					<td>${e.straﬂe}</td>
					<td>${e.hausNr}</td>
					<td>${e.plz}</td>
					<td>${e.ort}</td>
					<td class="hidden-xs">${e.land}</td>
					<td class="hidden-xs">${e.hinweis}</td>
				</tr>
 			</c:forEach>
		</tbody>
		</table>
	</jsp:body>
</my:base>