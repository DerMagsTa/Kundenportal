<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="de.fom.kp.persistence.Gender"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	Test Verbrauch
	</jsp:attribute>
	<jsp:attribute name="headline">
	Verbrauch für ${zaehler.energieArt}zähler: ${zaehler.zaehlerNr} <br>
	auf Entnahmestelle: ${entnahmestelle.straße} ${entnahmestelle.hausNr}, ${entnahmestelle.plz} ${entnahmestelle.ort}
	</jsp:attribute>
	<jsp:body>
	<script type="text/javascript">
		var getUrlParameter = function getUrlParameter(sParam) {
			var sPageURL = decodeURIComponent(window.location.search
					.substring(1)), sURLVariables = sPageURL.split('&'), sParameterName, i;

			for (i = 0; i < sURLVariables.length; i++) {
				sParameterName = sURLVariables[i].split('=');

				if (sParameterName[0] === sParam) {
					return sParameterName[1] === undefined ? true
							: sParameterName[1];
				}
			}
		};

/* 		function getMesswerte() {
			$.ajax({
				url : "${pageContext.request.contextPath}/api/zaehlerstaende?id=" + getUrlParameter("zid"),
				context : document.body
			}).done(function(data) {
				$("#Messwerte > tbody").empty();
				var html = "";
				$.each(data, function(i, m) {
					html += "<tr><td>" + m.ablesedatum + "</td>";
					html += "<td>" + m.messwert + "</td></tr>";
				});
				$("#Messwerte > tbody").append(html);
			});
		}; */

		$(document).ready(function() {

			$("#Datumvon").datepicker();
			$("#Datumvon").datepicker( "option", "dateFormat", "yy-mm-dd");
			$("#Datumbis").datepicker();
			$("#Datumbis").datepicker( "option", "dateFormat", "yy-mm-dd");
			// bisherige Zählerstände anzeigen
			//getMesswerte();

			// neuen Zählerstand abspeichern und Tabelle neu laden
			$("#anzeigen").click(function() {
				var url = "${pageContext.request.contextPath}/verbrauch.html?zid="+ getUrlParameter("zid")+"&eid=" + getUrlParameter("eid")+ "&datumvon="+ $("#Datumvon").val()+ "&datumbis="+ $("#Datumbis").val();
				window.location = url;
			}); 
		});
		</script>
		<div class="container">
			<form class="form-inline" action="">
				<div class="form-group">
					<label for="Datumvon"><fmt:message key="i18n.DatumVon" /></label> 
					<input type="text" class="form-control" id="Datumvon" size="20" placeholder="TT.MM.JJJJ">
				</div>
				<div class="form-group">
					<label for="Datumbis"><fmt:message key="i18n.DatumBis" /></label> 
					<input type="text" class="form-control" id="Datumbis" size="20" placeholder="TT.MM.JJJJ">
				</div>
			</form>			
			<button id="anzeigen" class="btn btn-primary"><fmt:message key="i18n.Anzeigen" /></button>
		</div>
		
		<table class="table table-hover" style="margin-top: 20px; max-width: 500px;">
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