<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	Z�hler
	</jsp:attribute>
	<jsp:attribute name="headline">
	Hier werden testweise alle Z�hler angezeigt
	</jsp:attribute>
	<jsp:body>
		<table class="table table-hover">
		<thead>
			<tr>
				<th><fmt:message key="i18n.ID"/></th>
				<th><fmt:message key="i18n.Entnahmestelle"/></th>
				<th><fmt:message key="i18n.Energieart"/></th>
				<th><fmt:message key="i18n.Z�hler-Nr"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${zaehlerliste}" var="z">
				<tr>
					<td>${z.id}</td>
					<td>${z.entnahmestelleId}</td>
					<td>${z.energieArt}</td>
					<td>${z.zaehlerNr}</td>
				</tr>
 			</c:forEach>
		</tbody>
		</table>
	</jsp:body>
</my:base>