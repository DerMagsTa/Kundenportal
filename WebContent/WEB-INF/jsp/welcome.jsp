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
		<p><label for="anrede" class="col-md-6">Anrede </label> <input type="text" name="anrede" id = "anrede" class="col-md-6" value="${personform.anrede}"><br/><p>
		<p><label for="vorname" class="col-md-6">Vorname</label> <input type="text" name="vorname" id = "vorname" class="col-md-6" value="${personform.vorname}"><br/></p>
		<p><label for="nachname" class="col-md-6">Nachname</label> <input type="text" name="nachname" id = "nachname" class="col-md-6" value="${personform.nachname}"><br/><p>
		<p><label for="geburtstag" class="col-md-6">Geburtsdatum</label> <input type="date" name="geburtstag" id = "geburtstag" class="col-md-6" value="${personform.geburtsdatum}"><br/><p>	
		<p><label for="strasse" class="col-md-6">Straße</label> <input type="text" name="strasse" id = "strasse" class="col-md-6" value="${personform.straße}"><br/><p>
		<p><label for="hausnr" class="col-md-6">HausNr.</label> <input type="text" name="hausnr" id = "hausnr" class="col-md-6" value="${personform.hausNr }"><br/></p>
		<p><label for="plz" class="col-md-6">PLZ</label> <input type="text" name="plz" id = "plz" class="col-md-6" value="${personform.plz}"><br/>
		<p><label for="ort" class="col-md-6">Ort</label> <input type="text" name="ort" id = "ort" class="col-md-6" value="${personform.ort}"><br/><p>
		<p><label for="email" class="col-md-6">E-Mail</label> <input type="email" name="email" id = "email" class="col-md-6" value="${personform.email}"><br/></p>
		<p><label for="land" class="col-md-6">Land</label> <input type="text" name="land" id = "land" class="col-md-6" value="${personform.land}"><br/></p>
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
		<ul>
			<c:forEach items="${entnahmestellen}" var="e">
				<li><h4>${e.straße} ${e.hausNr}, ${e.plz} ${e.ort} ${e.land}</h4>
				<p>${e.hinweis}</p></li>
				<p><a href="<c:url value="/entnahmestelle.html?eid=${e.id}"/>"><button type="button" class="btn btn-warning btn-xs"><fmt:message key="i18n.Entnahmestelle-ändern"/></button></a>
					<a href="<c:url value="/zaehler.html"/>"><button type="button" class="btn btn-success btn-xs" onclick=submit_zaehler();><fmt:message key="i18n.Zaehler-hinzufügen"/></button></a></p>
				<table class="table table-condensed">
				<tbody>
				<c:forEach items="${e.zaehler}" var="z">
					<tr>
						<td>${z.energieArt}:</td>
						<td>Nr. ${z.zaehlerNr}</td>
						<td style="width: 100px"><a href="<c:url value="/zaehlerstaende.html?zid=${z.id}&eid=${e.id}"/>"><button type="button" class="btn btn-primary btn-xs"><fmt:message key="i18n.Zählerstände"/></button></a></td>
						<td style="width: 100px"><a href="<c:url value="/verbrauch.html?zid=${z.id}&eid=${e.id}"/>"><button type="button" class="btn btn-primary btn-xs"><fmt:message key="i18n.Verbrauch"/></button></a></td>
					<tr>
					</c:forEach>
				</tbody>
				</table>
				
			</c:forEach>
			<li><a href="<c:url value="/entnahmestelle.html"/>"><button type="button" class="btn btn-success btn-xs"><fmt:message key="i18n.Entnahmestelle-hinzufügen"/></button></a>
		</ul>
	</div>
	  <script>
  function submit_zaheler(){
	  reqeust.setParameter("eid",e.id);
	  
  }
  </script>
   </jsp:body>

</my:base>