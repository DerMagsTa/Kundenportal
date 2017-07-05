<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    <title><fmt:message key="i18n.Titel_Login"/></title>

        <!-- Bootstrap core CSS -->
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">


   <style type="text/css">
	   body {
		  padding-top: 40px;
		  padding-bottom: 40px;
		  background-color: #eee;
		}
		
		.form-signin {
		  max-width: 330px;
		  padding: 15px;
		  margin: 0 auto;
		}
		.form-signin .form-signin-heading,
		.form-signin .checkbox {
		  margin-bottom: 10px;
		}
		.form-signin .checkbox {
		  font-weight: normal;
		}
		.form-signin .form-control {
		  position: relative;
		  height: auto;
		  -webkit-box-sizing: border-box;
		     -moz-box-sizing: border-box;
		          box-sizing: border-box;
		  padding: 10px;
		  font-size: 16px;
		}
		.form-signin .form-control:focus {
		  z-index: 2;
		}
		.form-signin input[type="email"] {
		  margin-bottom: -1px;
		  border-bottom-right-radius: 0;
		  border-bottom-left-radius: 0;
		}
		.form-signin input[type="password"] {
		  margin-bottom: 10px;
		  border-top-left-radius: 0;
		  border-top-right-radius: 0;
		}
		.register {
		max-width: 330px;
		margin: 0 auto;
		}
   </style>
  </head>

  <body>

    <div class="container">

      <form class="form-signin" method="post" action="j_security_check">
		
        <h2 class="form-signin-heading"><fmt:message key="i18n.Überschrift_Login"/></h2>
        <label for="inputEmail" class="sr-only"><fmt:message key="i18n.Email"/></label>
        <input type="email" name="j_username" id="inputEmail" class="form-control" placeholder=<fmt:message key="i18n.Email"/> required autofocus>
        <label for="inputPassword" class="sr-only"><fmt:message key="i18n.Passwort"/></label>
        <input type="password" name="j_password" id="inputPassword" class="form-control" placeholder=<fmt:message key="i18n.Passwort"/> required>

        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="i18n.einloggen"/></button>
      </form>
      
      <div class="register">
      	<a href="<c:url value="/register.html"/>"><button class="btn btn-lg btn-success btn-block"><fmt:message key="i18n.registrieren"/></button></a>
		</div>
		
    </div>   
  </body>
</html>
