<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="de.fom.kp.persistence.Gender"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>


<script type="text/javascript">
  
 
</script>
  
<my:base>
	<jsp:attribute name="title">
	Test Verbrauch
	</jsp:attribute>
	<jsp:attribute name="headline">
	Verbrauch f�r ${zaehler.energieArt}z�hler: ${zaehler.zaehlerNr} <br>
	auf Entnahmestelle: ${entnahmestelle.stra�e} ${entnahmestelle.hausNr}, ${entnahmestelle.plz} ${entnahmestelle.ort}
	</jsp:attribute>
	<jsp:attribute name="zusatz">
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		google.charts.load('current', {packages: ['corechart']});
	</script>
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
			
			$("#Datumvon").datepicker({ dateFormat: "<fmt:message key="i18n.datepatternpicker"/>" });
			$("#Datumbis").datepicker({ dateFormat: "<fmt:message key="i18n.datepatternpicker"/>" });
			// neuen Z�hlerstand abspeichern und Tabelle neu laden
// 			$("#anzeigen").click(function() {
// 				var url = "${pageContext.request.contextPath}/verbrauch.html?zid="+ getUrlParameter("zid")+"&eid=" 
// 						+ getUrlParameter("eid")+ "&datumvon="+ $("#Datumvon").val()+ 
// 						"&datumbis="+ $("#Datumbis").val()+ "&mode="+ $("#mode").val();
// 				window.location = url;
// 			}); 
			});
		
		</script>
		<form method="post" class="form-inline">
		<div class="container">
				<div class="form-group">
					<label for="Datumvon"><fmt:message key="i18n.DatumVon" /></label> 
					<input type="text" class="form-control" id="Datumvon" name="Datumvon"size="20" value="${verbrauchsForm.from}" placeholder="<fmt:message key="i18n.datepattern"/>">
				</div>
				<div class="form-group">
					<label for="Datumbis"><fmt:message key="i18n.DatumBis" /></label> 
					<input type="text" class="form-control" id="Datumbis" name="Datumbis" size="20" value="${verbrauchsForm.to}" placeholder="<fmt:message key="i18n.datepattern"/>">
				</div>
				<br>
				<br>
				<div class="form-group">
					<label for="berechnungsmodus"><fmt:message key="i18n.BMode" /></label> <br>
							 	<input type="radio"  name="mode" value="each"> 
					 	<label for="mode_e"><fmt:message key="i18n.BMode_for_each" /></label> 
					 	<input type="radio" name="mode" value="month"> 
					 	<label for="mode_m"><fmt:message key="i18n.BMode_month" /></label>
					 	<input type="radio"  name="mode" value="year"> 
					 	<label for="mode_y"><fmt:message key="i18n.BMode_year" /></label> 
				</div>
			<br>
			<br>
			<input type="submit" class="btn btn-success" id="anzeigen" value="<fmt:message key="i18n.Anzeigen"/>" name="anzeigen" />
		</div>
		</form>
		
		<table class="table table-hover" style="margin-top: 20px; max-width: 500px;">
		<thead>
			<tr>
				<th><fmt:message key="i18n.DatumVon" /></th>
				<th><fmt:message key="i18n.DatumBis" /></th>
				<th><fmt:message key="i18n.Verbrauch" /></th>
				<th><fmt:message key="i18n.Einheit" /></th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${verbrauchsForm.vl}" var="v">
				<tr>
					<td>${v.from}</td>
					<td>${v.to}</td>
					<td>${v.verbrauch}</td>
					<td>${v.unit}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
		<div id="chart_div"></div>
		<script>
		google.charts.load('current', {packages: ['corechart']});
		  google.charts.setOnLoadCallback(drawColColors);
		  
function drawColColors() {
      var data = new google.visualization.DataTable();
      data.addColumn('string', '<fmt:message key="i18n.Zeitraum" />');
      data.addColumn('number', '<fmt:message key="i18n.Verbrauch" />');


      <c:forEach items="${verbrauchsForm.vl}" var="v">
      data.addRow(['${v.from}-${v.to}', ${v.verbrauchD} ]);
      </c:forEach>

      var options = {
        title: '<fmt:message key="i18n.Verbrauch" />',
        colors: ['#9575cd', '#33ac71'],
        hAxis: {
          title: '<fmt:message key="i18n.Zeitraum" />',
         
          viewWindow: {
            min: [100, 300, 0],
            max: [100, 300, 0]
          }
        },
        vAxis: {
         
          title: '<fmt:message key="i18n.Verbrauch" />',
          viewWindow: {
              min: [100, 300, 0],
              max: [100, 300, 0]
            }
        }
        
      };
      var formatter = new google.visualization.NumberFormat({decimalSymbol: '<fmt:message key="i18n.decimalSymbol" />', groupingSymbol: '<fmt:message key="i18n.groupingSymbol" />' });
      formatter.format(data, 1);
      
      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
      chart.draw(data, options);
}
      </script>
      </jsp:body>

</my:base>