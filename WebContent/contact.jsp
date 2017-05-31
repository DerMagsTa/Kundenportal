<!DOCTYPE html>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<html>
<head>
<meta charset="UTF-8">
<title>Kontaktanfrage</title>
</head>
<body>
<h1>Beispiel für kleine allInOne "Webanwendung"</h1>
<%
// Hilfsvariable
boolean success = false;
// identifizieren ob Formular abgeschickt wurde
if(request.getParameter("send")!=null){
	// Fehlerüberprüfung
	if(StringUtils.isBlank(request.getParameter("email"))||StringUtils.isBlank(request.getParameter("subject"))||StringUtils.isBlank(request.getParameter("message"))){
		%>
		<span style="color: red">Bitte wählen Sie einen Empfänger aus und geben Sie einen Betreff und die Nachricht ein.</span>
		<%
	}else{
		// alles ok, E-Mail senden und Hilfsvariable setzen
		// (email senden fehlt hier, kommt später)
		success = true;
	}
}
// Falls E-Mail versendet wurde
if(success){
%>
	<span style="color: green">Nachricht an ${param.email} gesendet.</span>
<%	
// Beim ersten Aufruf oder bei Fehlern
}else{
%>
	<form action="" method="post">
	Empfänger:<br/>
	<select name="email">
	<option value="">--auswählen--</option>
	<%
	// E-Mail Adressen für select laden
	Class.forName("com.mysql.jdbc.Driver");
	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/wp?user=wp&password=wp");
	ResultSet rs = conn.createStatement().executeQuery("select * from person");
	while(rs.next()) {
		%>
		<option value="<%=rs.getString("email")%>"><%=rs.getString("email")%></option>
		<%
	}
	%>
	</select><br/>
	Betreff:<br/>
	<input type="text" name="subject" value="${param.subject}"><br/>
	Nachricht:<br/>
	<textarea name="message">${param.message}</textarea><br/>
	<input type="submit" name="send" value="senden">
	</form>
<%} %>
</body>
</html>