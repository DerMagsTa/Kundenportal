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
	<form method="post">
		<h2>Meine Daten</h2>
		<fieldset id="MeineDaten">

		<input type="hidden" name="id" value="${personform.id}">
		<input type="hidden" name="admin" id="admin" value="${personform.admin}">
		<p><label for="anrede">Anrede</label> <input type="text" name="anrede" id = "anrede" value="${personform.anrede}"><br/><p>
		<p><label for="vorname">Vorname</label> <input type="text" name="vorname" id = "vorname" value="${personform.vorname}"><br/></p>
		<p><label for="nachname">Nachname</label> <input type="text" name="nachname" id = "nachname" value="${personform.nachname}"><br/><p>
		<p><label for="geburtstag">Geburtsdatum</label> <input type="date" name="geburtstag" id = "geburtstag" value="${personform.geburtsdatum}"><br/><p>	
		<p><label for="strasse">Straße</label> <input type="text" name="strasse" id = "strasse" value="${personform.straße}"><br/><p>
		<p><label for="hausnr">HausNr.</label> <input type="text" name="hausnr" id = "hausnr" value="${personform.hausNr }"><br/></p>
		<p><label for="plz">PLZ</label> <input type="text" name="plz" id = "plz" value="${personform.plz}"><br/>
		<p><label for="ort">Ort</label> <input type="text" name="ort" id = "ort" value="${personform.ort}"><br/><p>
		<p><label for="email">E-Mail</label> <input type="email" name="email" id = "email" value="${personform.email}"><br/></p>
		<p><label for="land">Land</label> <input type="text" name="land" id = "land" value="${personform.land}"><br/></p>
		</fieldset>
		<input type="submit" class="btn btn-default" value="Ändern" name="MeineDatenÄndern" id="MeineDatenÄndern" onclick="ändern();">
		<input type="submit" class="btn btn-default" value="Speichern" name="Speichern" id="Speichern">
		<script>
			//alert("test");
 		    var e = ${personform.changemode};
  			if (e==true){
 	 			document.getElementById("MeineDaten").removeAttribute('disabled');
 	 			document.getElementById("Speichern").style.visibility="visible";
 	 			document.getElementById("MeineDatenÄndern").style.visibility="hidden";
  			}else{
 	 			document.getElementById("MeineDaten").setAttribute('disabled','disabled')
 				document.getElementById("Speichern").style.visibility="hidden";
 	 			document.getElementById("MeineDatenÄndern").style.visibility="visible";
  			}
		</script>
	    <script type="text/javascript">
		function ändern(){
			document.getElementById("MeineDaten").removeAttribute('disabled');
			document.getElementById("Speichern").style.visibility="visible";
			document.getElementById("MeineDatenÄndern").style.visibility="hidden";
		}
		</script>
		</form>
		
	</div>
	<div class="col-md-8">
		<h2>Meine Entnahmestellen</h2>
		<table class="table table-hover">
		<thead>
			<tr>
				<th><fmt:message key="i18n.Straße"/></th>
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
					<td>${e.straße}</td>
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
					<th><fmt:message key="i18n.ZählerNr"/></th>
				</tr>
					<c:forEach items="${e.zaehler}" var="z">
						<tr>
							<td></td>
							<td>${z.id}</td>
							<td>${z.entnahmestelleId}</td>
							<td>${z.energieArt}</td>
							<td>${z.zaehlerNr}</td>
							<td><a href="<c:url value="/zaehlerstaende.htm?id=${z.id}"/>"><button type="button" class="btn btn-primary btn-xs"><fmt:message key="i18n.Zählerstände"/></button></a></td>
						</tr>
					</c:forEach>
 			</c:forEach>
		</tbody>
		</table>
	</div>

		
   </jsp:body>
	
	
</my:base>