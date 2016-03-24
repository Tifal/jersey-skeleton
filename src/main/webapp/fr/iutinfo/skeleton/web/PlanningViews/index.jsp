<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <jsp:include page="/layout/head.jsp"/>
    <meta charset="utf-8">
    <title>Planning</title>
  </head>
  <body>
    <jsp:include page="/layout/navbar.jsp"/>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <h1>Planning</h1>
                <ul class="list-group">
                <c:forEach var="command" items="${it}">
                    <li class="list-group-item">
                    	${command.id} : <a href="command/${command.id}">${command.price} €</a> <br>
                    	Retrait : ${command.addressRetrait} le ${command.dateRetrait} <br />
                    	Livraison : ${command.addressLivraison} le ${command.dateLivraison} <br />
                    </li>
                </c:forEach>
                </ul>
            </div>
        </div>
    </div>
  </body>
</html>