<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="my" tagdir="/WEB-INF/tags"%>
<my:base>
	<jsp:attribute name="title">
	Zähler
	</jsp:attribute>
	<jsp:attribute name="headline">
	Zähler anlegen / bearbeiten
	</jsp:attribute>
	<jsp:body>
		<div class="container">
		<form class="form-horizontal" method="post">
			<input type="hidden" name="id" value="${zform.id}">
			<input type="hidden" name="entnahmestellenId"
					value="${zform.entnahmestellenId}">
			
			<div class="form-group">
				<label for="zaehlerNr" class="col-sm-2 control-label"><fmt:message
							key="i18n.ZählerNr" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="zaehlerNr"
							name="zaehlerNr" placeholder="ZaehlerNR"
							value="${zform.zaehlerNr}">
				</div>
				<my:error field="zaehlerNr" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="energieArt" class="col-sm-2 control-label"><fmt:message
							key="i18n.energieArt" />*</label> 
				<div class="col-sm-10">
				<select class="form-control" id="energieArt" name="energieArt">
    				<c:forEach items="${EnergieArten}" var="ea">
    					<option>${ea}</option>;
    				</c:forEach>
    			</select>
			</div>
			</div>
			<div class="col-sm-offset-2 col-sm-10">
				<p>* benötigt</p>
				<input type="submit" class="btn btn-success" value=<fmt:message key="i18n.save"/>
						name="zspeichern" id="zsave"> 
				<input type="submit" class="btn btn-danger" value=<fmt:message key="i18n.dele"/>
						name="zdele" id="zdele">
				<script>
				//wenn die ID nicht angegeben ist soll ein zähler angelegt werden, daher "löschen" Button ausbelden
				//un den Text auf "anlegen" ändern!
			    if ("${zform.id}" == "") {
			    	 document.getElementById('zdele').style.visibility = 'hidden';
			    	 document.getElementById('zsave').setAttribute('value','<fmt:message key="i18n.create"/>');
			    }
				</script>
			</div>
		</form>
		</div>
	</jsp:body>
</my:base>