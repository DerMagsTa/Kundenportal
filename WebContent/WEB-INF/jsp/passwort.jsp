<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	<fmt:message key="i18n.Titel_Passwort"/>
	</jsp:attribute>
	<jsp:attribute name="headline">
	<fmt:message key="i18n.Titel_Passwort"/>
	</jsp:attribute>
	<jsp:body>
	<c:if test="${pwupdate eq 'erfolgreich'}">
		<span style="color: green"><fmt:message key="i18n.Passwort_erfolgreich"/></span>
	</c:if>
	<c:if test="${pwupdate eq 'fehlgeschlagen'}">
		<span style="color: red"><fmt:message key="i18n.Passwort_fehlgeschlagen"/></span>
	</c:if>
<%-- 	<%
	if (request.getParameter("pwupdate").equals("erfolgreich")){     
		%>
		<span style="color: green"><fmt:message key="i18n.Passwort_erfolgreich"/></span>
		<%
	}else if (request.getParameter("pwupdate").equals("fehlgeschlagen")){
		%>
		<span style="color: red"><fmt:message key="i18n.Passwort_fehlgeschlagen"/></span>
		<%
	}
	%> --%>
		<div class="container">
		<form class="form-horizontal" method="post">
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Passwort_alt"/>*</label> 
				<div class="col-sm-10">
				<input type="password" class="form-control" id="passwort_alt" name="passwort_alt" value="${pwform.passwort_alt}">
				</div>
				<my:error field="passwort_alt" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Passwort_neu"/>*</label> 
				<div class="col-sm-10">
				<input type="password" class="form-control" id="passwort_neu" name="passwort_neu" value="${pwform.passwort_neu}">
				</div>
				<my:error field="passwort_neu" errorlist="${errors}"></my:error>
			</div>
			
			<div class="form-group">
				<label for="straße" class="col-sm-2 control-label"><fmt:message key="i18n.Passwort_wiederholung"/>*</label> 
				<div class="col-sm-10">
				<input type="password" class="form-control" id="passwort_wiederholung" name="passwort_wiederholung" value="${pwform.passwort_wiederholung}">
				</div>
				<my:error field="passwort_wiederholung" errorlist="${errors}"></my:error>
			</div>
			
			
			<div class="col-sm-offset-2 col-sm-10">
				<p>* <fmt:message key="i18n.benötigt"/></p>
				<input type="submit" class="btn btn-success" value=<fmt:message key="i18n.speichern"/> name="pwsave" id="pwsave">
			</div>

		</form>
		</div>
		
	</jsp:body>
</my:base>