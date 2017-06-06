<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="de.fom.kp.persistence.Gender"%>
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
	<form method="post">
		<h2>Meine Daten</h2>
		<fieldset id="MeineDaten">
		<input type="hidden" name="id" value="${personform.id}">
		<input type="hidden" name="admin" value="${personform.admin}">
		<p><label for="anrede">Anrede</label> <input type="text" name="anrede" id = "anrede" value="${personform.anrede}"><br/><p>
		<p><label for="vorname">Vorname</label> <input type="text" name="vorname" id = "vorname" value="${personform.vorname}"><br/></p>
		<p><label for="nachname">Nachname</label> <input type="text" name="nachname" id = "nachname" value="${personform.nachname}"><br/><p>
		<p><label for="geburtstag">Geburtsdatum</label> <input type="date" name="geburtstag" id = "geburtstag" value="${personform.geburtsdatum}"><br/><p>	
		<p><label for="strasse">Straﬂe</label> <input type="text" name="strasse" id = "strasse" value="${personform.straﬂe}"><br/><p>
		<p><label for="hausnr">HausNr.</label> <input type="text" name="hausnr" id = "hausnr" value="${personform.hausNr }"><br/></p>
		<p><label for="plz">PLZ</label> <input type="text" name="plz" id = "plz" value="${personform.plz}"><br/>
		<p><label for="ort">Ort</label> <input type="text" name="ort" id = "ort" value="${personform.ort}"><br/><p>
		<p><label for="email">E-Mail</label> <input type="email" name="email" id = "email" value="${personform.email}"><br/></p>
		<p><label for="land">Land></label> <input type="text" name="land" id = "land" value="${personform.land}"><br/></p>

  
  
		</fieldset>
		<input type="submit" class="btn btn-default" value="ƒndern" name="MeineDatenƒndern" onclick="‰ndern();">
		<input type="submit" class="btn btn-default" value="Speichern" name="Speichern">
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
 			</c:forEach>
		</tbody>
		</table>
	</div>

		
   </jsp:body>
	
	
</my:base>