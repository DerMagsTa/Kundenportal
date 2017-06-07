<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	Welcome
	</jsp:attribute>
	<jsp:attribute name="headline">
	Willkommen ${user.anrede} ${user.vorname} ${user.nachname}
	</jsp:attribute>
	
	<jsp:body>
	<div class="col-md-4">
	<form>
		<h2>Meine Daten</h2>
		<fieldset id="MeineDaten">
		<p><label for="anrede">Anrede</label> <input type="text" value="${user.anrede}"><br/><p>
		<p><label for="vorname">Vorname</label> <input type="text" value="${user.vorname}"><br/></p>
		<p><label for="nachname">Nachname</label> <input type="text" value="${user.nachname}"><br/><p>
		<p><label for="geburtstag">Geburtsdatum</label> <input type="text" value="${user.geburtsdatum}"><br/><p>	
		<p><label for="strasse">Straﬂe</label> <input type="text" value="${user.straﬂe}"><br/><p>
		<p><label for="hausnr">HausNr.</label> <input type="text" value="${user.hausNr }"><br/></p>
		<p><label for="plz">PLZ</label> <input type="text" value="${user.plz}"><br/>
		<p><label for="ort">Ort</label> <input type="text" value="${user.ort}"><br/><p>
		<p><label for="email">E-Mail</label> <input type="text" value="${user.email}"><br/></p>
		</fieldset>
		<input type="submit" class="btn btn-default" value="ƒndern" name="MeineDatenƒndern" onclick="‰ndern();">
		
		</form>
	</div>
	<div class="col-md-8">
		<h2>Meine Entnahmestellen</h2>
		<table class="table table-hover">
		<thead>
			<tr>
				<th><fmt:message key="i18n.Straﬂe"/></th>
				<th><fmt:message key="i18n.Haus-Nr"/></th>
				<th><fmt:message key="i18n.PLZ"/></th>
				<th><fmt:message key="i18n.Ort"/></th>
				<th class="hidden-xs"><fmt:message key="i18n.Land"/></th>
				<th class="hidden-xs"><fmt:message key="i18n.Hinweis"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${entnahmestellen}" var="e">
				<tr>
					<td>${e.straﬂe}</td>
					<td>${e.hausNr}</td>
					<td>${e.plz}</td>
					<td>${e.ort}</td>
					<td class="hidden-xs">${e.land}</td>
					<td class="hidden-xs">${e.hinweis}</td>
				</tr>
				<tr>
					<th></th>
					<th><fmt:message key="i18n.Z-ID"/></th>
					<th><fmt:message key="i18n.E-ID"/></th>
					<th><fmt:message key="i18n.E-Art"/></th>
					<th><fmt:message key="i18n.Z‰hlerNr"/></th>
				</tr>
					<c:forEach items="${e.zaehler}" var="z">
						<tr>
							<td></td>
							<td>${z.id}</td>
							<td>${z.entnahmestelleId}</td>
							<td>${z.energieArt}</td>
							<td>${z.zaehlerNr}</td>
							<td><a href="<c:url value="/zaehlerstaende.htm?id=${z.id}"/>"><button type="button" class="btn btn-primary btn-xs"><fmt:message key="i18n.Z‰hlerst‰nde"/></button></a></td>
						</tr>
					</c:forEach>
 			</c:forEach>
		</tbody>
		</table>
	</div>
		<Script>
		function ‰ndern() {	  
		    var e = document.getElementById('MeineDaten');
		    e.removetAttribute('disabled');
		}
		</Script>
		
   </jsp:body>
	
	
</my:base>