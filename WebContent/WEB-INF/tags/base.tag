<%@tag description="page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="headline" fragment="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="MessageResources" scope="session" />

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title><jsp:invoke fragment="title"/></title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">


    <!-- Custom styles for this template -->
    <link href="<c:url value="/css/wp.css"/>" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="<c:url value="/js/jquery.js"/>"></script>

	<!-- jQuery UI -->
	<script src="<c:url value="/js/jquery-ui.min.js"/>"></script>
 	<link href="<c:url value="/css/jquery-ui.min.css"/>" rel="stylesheet">
	
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		google.charts.load('current', {packages: ['corechart']});
	</script>
  </head>

  <body>
	<t:menu/>

    <!-- Begin page content -->
    <div class="container">
      <div class="page-header">
        <h1><jsp:invoke fragment="headline"/></h1>
      </div>
      <jsp:doBody/>
    </div>

    <footer class="footer">
      <div class="container">
        <p class="text-muted">Current User: ${user.email}</p>
      </div>
    </footer>
    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>

  </body>
</html>