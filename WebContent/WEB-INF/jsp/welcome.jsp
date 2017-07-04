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
	<!-- Zeigt die persˆnlichen Daten der eingelogten Person an -->
		<h2>Meine Daten</h2>
		<p>
		<label class="col-md-6">Anrede </label> 	
		<span class="col-md-6">${personform.anrede}</span><br/>
		</p>
		<p>
		<label class="col-md-6">Vorname</label>  	
		<span class="col-md-6">${personform.vorname}</span><br/>
		</p>
		<p>
		<label class="col-md-6">Nachname</label>  
		<span class="col-md-6">${personform.nachname}</span><br/>
		</p>
		<p>
		<label class="col-md-6">E-Mail</label>  
		<span class="col-md-6">${personform.email}</span><br/>
		</p>
		<p>
		<label class="col-md-6">Geburtsdatum</label>  
		<span class="col-md-6">${personform.geburtsdatum}</span><br/>
		</p>
		<p><label class="col-md-6">Straﬂe</label>  
		<span class="col-md-6">${personform.straﬂe}</span><br/><p>
		<p>
		<label class="col-md-6">HausNr.</label>  
		<span class="col-md-6">${personform.hausNr}</span><br/>
		</p>
		<p>
		<label class="col-md-6">PLZ</label>  
		<span class="col-md-6">${personform.plz}</span><br/>
		</p>
		<p>
		<label class="col-md-6">Ort</label>  
		<span class="col-md-6">${personform.ort}</span><br/>
		</p>
		<p>
		<label class="col-md-6">Land</label>  
		<span class="col-md-6">${personform.land}</span><br/>
		</p>
		<p class="col-md-6"><a href="<c:url value="/register.html?id=${personform.id}"/>"><button type="button" class="btn btn-warning btn-xs"><fmt:message key="i18n.‰ndern"/></button></a></p>
	</div>
	
	<div class="col-md-8">
	<!-- Zeigt alle Entnahmestellen mit dazugehˆrigen Z‰hlern der eingelogten Person an -->
		<h2>Meine Entnahmestellen</h2>
		<ul>
			<c:forEach items="${pdbuffer.es}" var="e">
				<li><h4>${e.straﬂe} ${e.hausNr}, ${e.plz} ${e.ort} ${e.land}</h4>
				<p>${e.hinweis}</p></li>
				<p><a href="<c:url value="/entnahmestelle.html?eid=${e.id}"/>"><button type="button" class="btn btn-warning btn-xs"><fmt:message key="i18n.Entnahmestelle-‰ndern"/></button></a>
					<a href="<c:url value="/zaehler.html?eid=${e.id}"/>"><button type="button" class="btn btn-success btn-xs"><fmt:message key="i18n.Zaehler-hinzuf¸gen"/></button></a>
					</p>
				<table class="table table-condensed">
				<tbody>
				<c:forEach items="${e.zaehler}" var="z">
					<tr>
						<td>${z.energieArt}:</td>
						<td>Nr. ${z.zaehlerNr}</td>
						<td style="width: 50px"><a href="<c:url value="/zaehler.html?zid=${z.id}"/>"><button type="button" class="btn btn-warning btn-xs"><fmt:message key="i18n.‰ndern"/></button></a></td>
						<td style="width: 100px"><a href="<c:url value="/zaehlerstaende.html?zid=${z.id}&eid=${e.id}"/>"><button type="button" class="btn btn-primary btn-xs"><fmt:message key="i18n.Z‰hlerst‰nde"/></button></a></td>
						<td style="width: 100px"><a href="<c:url value="/verbrauch.html?zid=${z.id}&eid=${e.id}"/>"><button type="button" class="btn btn-primary btn-xs"><fmt:message key="i18n.Verbrauch"/></button></a></td>
					<tr>
					</c:forEach>
				</tbody>
				</table>
				
			</c:forEach>
			<li><a href="<c:url value="/entnahmestelle.html"/>"><button type="button" class="btn btn-success btn-xs"><fmt:message key="i18n.Entnahmestelle-hinzuf¸gen"/></button></a>
		</ul>
	</div>
   </jsp:body>

</my:base>