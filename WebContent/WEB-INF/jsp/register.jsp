<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	<fmt:message key="i18n.Titel_Registrierung"/>
	</jsp:attribute>
	<jsp:attribute name="headline">
	<fmt:message key="i18n.Titel_Registrierung"/>
	</jsp:attribute>
	<jsp:body>
		<div class="container">
		<form class="form-horizontal" method="post">
			<input type="hidden" name="id" value="${form.id}">
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Email" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="email" name="email" placeholder="max@musterman.de" value="${form.email}">
				</div>
				<my:error field="email" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Passwort" />*</label> 
				<div class="col-sm-10">
				<input type="password" class="form-control" id="passwort" name="passwort" value="${form.passwort}">
				</div>
				<my:error field="passwort" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Anrede" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="anrede" name="anrede" placeholder="Herr" value="${form.anrede}">
				</div>
				<my:error field="anrede" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Vorname" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="vorname" name="vorname" placeholder="Max" value="${form.vorname}">
				</div>
				<my:error field="vorname" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Nachname" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="nachname" name="nachname" placeholder="Mustermann" value="${form.nachname}">
				</div>
				<my:error field="nachname" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Geburtsdatum" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="geburtsdatum" name="geburtsdatum" placeholder="${form.dateFormatPattern}" value="${form.geburtsdatum}">
				</div>
				<my:error field="geburtsdatum" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Straße" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="straße" name="straße" placeholder="Musterstr." value="${form.straße}">
				</div>
				<my:error field="straße" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="hausNr" class="col-sm-2 control-label"><fmt:message key="i18n.Haus_Nr" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="hausNr" name="hausNr" placeholder="1a-3b" value="${form.hausNr}">
				</div>
				<my:error field="hausNr" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="plz" class="col-sm-2 control-label"><fmt:message key="i18n.PLZ" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="plz" name="plz" placeholder="01234" value="${form.plz}">
				</div>
				<my:error field="plz" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="ort" class="col-sm-2 control-label"><fmt:message key="i18n.Ort" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="ort" name="ort" placeholder="Musterort" value="${form.ort}">
				</div>
				<my:error field="ort" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="land" class="col-sm-2 control-label"><fmt:message key="i18n.Land" />*</label> 
				<div class="col-sm-10">
				<input type="text" class="form-control" id="land" name="land" placeholder="DE" value="${form.land}">
				</div>
				<my:error field="land" errorlist="${errors}"></my:error>
			</div>
			
			<div class="col-sm-offset-2 col-sm-10">
				<p>* <fmt:message key="i18n.benötigt"/></p>
				<input type="submit" class="btn btn-success" value=<fmt:message key="i18n.speichern"/> name="psave" id="psave">
				<input type="submit" class="btn btn-danger" value=<fmt:message key="i18n.löschen"/> name="pdele" id="pdele">
				<script>
				//wenn die ID nicht angegeben ist soll ein zähler angelegt werden, daher "löschen" Button ausbelden
				//un den Text auf "anlegen" ändern!
			    if ("${form.id}" == "") {
			    	 document.getElementById('pdele').style.visibility = 'hidden';
			    	 document.getElementById('psave').setAttribute('value','<fmt:message key="i18n.anlegen"/>');
			    }
				</script>
			</div>

		</form>
		</div>
		
	</jsp:body>
</my:base>