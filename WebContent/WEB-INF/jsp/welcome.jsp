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
	<form>
		<h2>Meine Daten</h2>
		<fieldset id="MeineDaten" disabled>
		<p><label for="anrede">Anrede</label> <input type="text" value="${user.anrede}"><br/><p>
		<p><label for="vorname">Vorname</label> <input type="text" value="${user.vorname}"><br/></p>
		<p><label for="nachname">Nachname</label> ${user.nachname}<br/><p>
		<p><label for="geburtstag">Geburtsdatum</label> ${user.geburtsdatum}<br/><p>	
		<p><label for="strasse">Straﬂe</label> ${user.straﬂe}<br/><p>
		<p><label for="hausnr">HausNr.</label> ${user.hausNr }<br/></p>
		<p><label for="plz">PLZ</label> ${user.plz}<br/>
		<p><label for="ort">Ort</label> ${user.ort}<br/><p>
		<p><label for="email">E-Mail</label> ${user.email}<br/></p>
		</fieldset>
		<input type="submit" class="btn btn-default" value="ƒndern" name="MeineDatenƒndern" onclick="‰ndern();">
		
		</form>

		<Script>
		function ‰ndern() {	  
		    var e = document.getElementById('MeineDaten');
		    e.removetAttribute('disabled');
		}
		</Script>
		
   </jsp:body>
	
	
</my:base>