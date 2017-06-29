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
					html += "<td style='text-align: right;'>" + m.messwert + "</td>";
					html += "<td>" + "<a href='${pageContext.request.contextPath}/zaehlerstaende.html?zid=" + getUrlParameter("zid") + "&eid=" + getUrlParameter("eid") +"&mid=" + m.id + "'><button type='button' class='btn btn-success btn-xs'><fmt:message key='i18n.ändern'/></button></a>" + "</td>";
					html += "<td>" + "<a href='${pageContext.request.contextPath}/zaehlerstaende.html?zid=" + getUrlParameter("zid") + "&eid=" + getUrlParameter("eid") +"&mid=" + m.id + "&del=true" + "'><button type='button' class='btn btn-danger btn-xs'><fmt:message key='i18n.dele'/></button></a>" + "</td></tr>";
				});
				$("#Messwerte > tbody").append(html);
			});
		};
		jQuery(function(jQuery)
				{
				 jQuery.datepicker.regional['de'] = {clearText: 'löschen', clearStatus: 'aktuelles Datum löschen',
				            closeText: 'schließen', closeStatus: 'ohne Änderungen schließen',
				            prevText: '          ',  nextText: 'Vor>', nextStatus: 'nächsten Monat zeigen',
				            currentText: 'heute', currentStatus: '',
				            monthNames: ['Januar','Februar','März','April','Mai','Juni',
				            'Juli','August','September','Oktober','November','Dezember'],
				            monthNamesShort: ['Jan','Feb','Mär','Apr','Mai','Jun',
				            'Jul','Aug','Sep','Okt','Nov','Dez'],
				            monthStatus: 'anderen Monat anzeigen', yearStatus: 'anderes Jahr anzeigen',
				            weekHeader: 'Wo', weekStatus: 'Woche des Monats',
				            dayNames: ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'],
				            dayNamesShort: ['So','Mo','Di','Mi','Do','Fr','Sa'],
				            dayNamesMin: ['So','Mo','Di','Mi','Do','Fr','Sa'],
				            dayStatus: 'Setze DD als ersten Wochentag', dateStatus: 'Wähle D, M d',
				            dateFormat: 'dd.mm.yy', firstDay: 1, 
				            initStatus: 'Wähle ein Datum', isRTL: false};
				// jQuery.datepicker.setDefaults(jQuery.datepicker.regional['de']);
				});
		
		$(function() {
			$("#ablesedatum").datepicker($.extend({}, $.datepicker.regional["<fmt:message key="i18n.datepickerregion"/>"], { dateFormat: "<fmt:message key="i18n.datepatternpicker"/>" }));
			
			// bisherige Zählerstände anzeigen
			getMesswerte();

			// neuen Zählerstand abspeichern und Tabelle neu laden
			$("#speichern").click(function() {
				$.ajax({
					url : "${pageContext.request.contextPath}/api/zaehlerstand?zid="+ getUrlParameter("zid")+ "&ablesedatum="+ $("#ablesedatum").val()+ "&messwert="+ $("#messwert").val()
				}).done(function() {
					getMesswerte();
				});
			});
		});
		</script>
				
		<div class="container">
			<c:forEach items="${errors}" var="e">
				${e.message }<br/>
			</c:forEach>
			<form class="form-inline" method="post">
				<input type="hidden" name="id" value="${mform.id}">
				<input type="hidden" name="zaehlerId" value="${mform.zaehlerId}">
				<div class="form-group">
					<label for="ablesedatum"><fmt:message key="i18n.Ablesedatum" /></label> 
					<input type="text" class="form-control" id="ablesedatum" name="ablesedatum" placeholder="${mform.dateFormatPattern}" value="${mform.ablesedatum}">
				</div>
				<div class="form-group">
					<label for="messwert"><fmt:message key="i18n.Zählerstand" /> (<fmt:message key="i18n.ZählerstandUnit${unit}" />)</label> 
					<input type="text" class="form-control" id="messwert" name="messwert" value="${mform.messwert}">
				</div>
				<input type="submit" class="btn btn-default" value="save" name="zspeichern">
			</form>
			<br>
			<!-- <button id="speichern" class="btn btn-primary"><fmt:message key="i18n.ZSpeichern" /></button> -->
		</div>
	
		<table id="Messwerte" class="table table-hover" style="margin-top: 20px; max-width: 400px;">
			<thead>
				<tr>
					<th><fmt:message key="i18n.Ablesedatum" /></th>
					<th><fmt:message key="i18n.Zählerstand" /> (<fmt:message key="i18n.ZählerstandUnit${unit}" />)</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
	
			</tbody>
		</table>
		<a href="<c:url value="/report/zaehlerstaende?id=${zaehler.id}"/>"><button type="button" class="btn btn-warning btn-xs"><fmt:message key="i18n.exportieren"/></button></a>
	</jsp:body>
</my:base>