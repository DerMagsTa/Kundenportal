<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Fixed navbar -->
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="<c:url value="/welcome.html"/>">Meine Übersicht</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              <img src="<c:url value="${flag}"/>">
               Locale <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="<c:url value="/welcome.html?locale=de"/>"><img src="<c:url value="/images/flag_de.png"/>"> Deutsch</a></li>
                <li><a href="<c:url value="/welcome.html?locale=en"/>"><img src="<c:url value="/images/flag_en.png"/>"> Englisch</a></li>
              </ul>
            </li>
            <li><a href="<c:url value="/logout.html"/>">Logout</a></li>
          </ul>
        </div>
      </div>
    </nav>