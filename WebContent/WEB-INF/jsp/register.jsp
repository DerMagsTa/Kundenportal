<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="de.fom.kp.persistence.Gender"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>
<my:base>
	<jsp:attribute name="title">
	Registrieren
	</jsp:attribute>
	<jsp:attribute name="headline">
	Registrierungsformular
	</jsp:attribute>
	<jsp:body>
	<%-- <c:forEach items="${errors}" var="e">
	${e.message }<br/>
	</c:forEach> --%>
		<form method="post">
			<input type="hidden" name="id" value="${form.id}">
			
			<p><label for="gender">gender</label><br/>
				<c:set var="enumValues" value="<%=Gender.values()%>"/>
				<c:forEach items="${enumValues}" var="g">
					<input type="radio" name="gender" id="gender.${g}" value="${g}"
					<c:if test="${form.gender eq g}"> checked="checked"</c:if>
					> <label for="gender.${g}">${g}</label>
				</c:forEach>
			</p>
			<p><label for="email">email*</label><br/>
			<%-- <input type="text" name="email" id="email" value="${form.email}" onblur="checkemail(this.value, '${form.id}')"> --%>
			<input type="text" name="email" id="email" value="${form.email}">
			<img src="images/clear.gif" id="emailcheck"/>
			<my:error field="email" errorlist="${errors}">Kritisch </my:error>
			</p>
			
			<p><label for="firstname">firstname</label><br/>
			<input type="text" name="firstname" id="firstname" value="${form.firstname}"></p>
			
			<p><label for="lastname">lastname</label><br/>
			<input type="text" name="lastname" id="lastname" value="${form.lastname}"></p>
			
			<p><label for="birthday">birthday</label><br/>
			<input type="text" name="birthday" id="birthday" value="${form.birthday}">
			<my:error field="birthday" errorlist="${errors}"/>
			
			</p>
			
			<p><label for="height">height</label><br/>
			<input type="text" name="height" id="height" value="${form.height}"></p>
			
			<p><label for="companyid">select company</label><br/>
			<select name="companyid" id="companyid">
				<option value="">--select--</option>
				<c:forEach items="${companylist}" var="company">
					<option value="${company.id}"
					<c:if test="${company.id eq form.companyid}"> selected="selected"</c:if>
					>${company.name}</option>
				</c:forEach>
			</select></p>
			
			<p><label for="newcompany">or insert new company</label><br/>
			<input type="text" name="newcompany" id="newcompany" value="${form.newcompany}"></p>
			
			<p><label for="interests">interests</label><br/>
				<c:forEach items="${interestlist}" var="interest">
					<input type="checkbox" name="interests" value="${interest.id}" id="interests${interest.id}"
					<c:if test="${form.interests.contains(interest.id)}"> checked="checked"</c:if>
					/> <label for="interests${interest.id}">${interest.name}</label>
				</c:forEach>
			</p>
			
			<p><label for="newsletter">newsletter</label><br/>
				<input type="checkbox" name="newsletter" id="newsletter"
				<c:if test="${form.newsletter}"> checked="checked"</c:if>
				/> <label for="newsletter">subscribe newsletter</label>
			</p>
			
			<p><label for="comment">comment</label><br/>
			<textarea rows="5" cols="50" name="comment">${form.comment}</textarea></p>
			
			
		<p>* required</p>
		<input type="submit" class="btn btn-default" value="save" name="register">
		</form>
	</jsp:body>
</my:base>