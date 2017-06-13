<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:base>
	<jsp:attribute name="title">
	Zählerstände
	</jsp:attribute>
	<jsp:attribute name="headline">
	Zählerstände für ${zaehler.energieArt}zähler: ${zaehler.zaehlerNr} <br>
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

		function getMesswerte() {
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
		};

		$(function() {

			$("#ablesedatum").datepicker();
			$("#ablesedatum").datepicker( "option", "dateFormat", "yy-mm-dd");
			
			// bisherige Zählerstände anzeigen
			getMesswerte();

			// neuen Zählerstand abspeichern und Tabelle neu laden
			$("#speichern").click(function() {
				$.ajax({
					url : "${pageContext.request.contextPath}/api/zaehlerstand?id="+ getUrlParameter("zid")+ "&ablesedatum="+ $("#ablesedatum").val()+ "&messwert="+ $("#messwert").val()
				}).done(function() {
					getMesswerte();
				});
			});
		});
		</script>
				
		<div class="container">
			<form class="form-inline" action="">
				<div class="form-group">
					<label for="ablesedatum"><fmt:message key="i18n.Ablesedatum" /></label> 
					<input type="text" class="form-control" id="ablesedatum" size="20" placeholder="TT.MM.JJJJ">
				</div>
				<div class="form-group">
					<label for="messwert"><fmt:message key="i18n.Zählerstand" /></label> 
					<input type="number" class="form-control" id="messwert">
				</div>
			</form>
			<button id="speichern" class="btn btn-primary"><fmt:message key="i18n.ZSpeichern" /></button>
		</div>
	
		<table id="Messwerte" class="table table-hover" style="margin-top: 20px; max-width: 300px;">
			<thead>
				<tr>
					<th><fmt:message key="i18n.Ablesedatum" /></th>
					<th><fmt:message key="i18n.Zählerstand" /></th>
				</tr>
			</thead>
			<tbody>
	
			</tbody>
		</table>
		
		
		<!--  -->
	</jsp:body>
</my:base>