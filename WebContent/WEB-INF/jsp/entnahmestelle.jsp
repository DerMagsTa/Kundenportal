<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	Entnahmestelle
	</jsp:attribute>
	<jsp:attribute name="headline">
	Entnahmestelle anlegen / bearbeiten
	</jsp:attribute>
	<jsp:body>
		<div class="container">
		<form class="form-horizontal" method="post">
			<input type="hidden" name="id" value="${eform.id}">
			<input type="hidden" name="personId" value="${eform.personId}">
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Straße" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="straße" name="straße" placeholder="Musterstr." value="${eform.straße}">
				</div>
				<my:error field="straße" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="hausNr" class="col-sm-2 control-label"><fmt:message key="i18n.Haus-Nr" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="hausNr" name="hausNr" placeholder="1a-3b" value="${eform.hausNr}">
				</div>
				<my:error field="hausNr" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="plz" class="col-sm-2 control-label"><fmt:message key="i18n.PLZ" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="plz" name="plz" placeholder="01234" value="${eform.plz}">
				</div>
				<my:error field="plz" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="ort" class="col-sm-2 control-label"><fmt:message key="i18n.Ort" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="ort" name="ort" placeholder="Musterort" value="${eform.ort}">
				</div>
				<my:error field="ort" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="land" class="col-sm-2 control-label"><fmt:message key="i18n.Land" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="land" name="land" placeholder="DE" value="${eform.land}">
				</div>
				<my:error field="land" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="hinweis" class="col-sm-2 control-label"><fmt:message key="i18n.Hinweis" /></label> 
				<div class="col-sm-10">
				<textarea class="form-control" rows="2" cols="50" name="hinweis">${eform.hinweis}</textarea>
				</div>
			</div>
			
			<div class="col-sm-offset-2 col-sm-10">
				<p>* benötigt</p>
				<input type="submit" class="btn btn-success" value=<fmt:message key="i18n.save"/> name="espeichern" id="esave">
				<input type="submit" class="btn btn-danger" value=<fmt:message key="i18n.dele"/> name="edele" id="edele">
				<script>
				//wenn die ID nicht angegeben ist soll ein zähler angelegt werden, daher "löschen" Button ausbelden
				//un den Text auf "anlegen" ändern!
			    if ("${eform.id}" == "") {
			    	 document.getElementById('edele').style.visibility = 'hidden';
			    	 document.getElementById('esave').setAttribute('value','<fmt:message key="i18n.create"/>');
			    }
				</script>
			</div>
		</form>
		</div>
	</jsp:body>
</my:base>