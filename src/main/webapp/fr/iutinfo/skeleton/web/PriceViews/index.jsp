<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <jsp:include page="/layout/head.jsp"/>
    <meta charset="utf-8">
    <title>Prix</title>
  </head>
  <body>
    <jsp:include page="/layout/navbar.jsp"/>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <h1>Planning</h1>
                <ul class="list-group">
                <c:forEach var="price" items="${it}">
                    <li class="list-group-item">
                    	<form class="form-inline"  action="/html/pricing/${price.item}" method="post">
                    		 <div class="form-group">
							    <label for="price">${price}</label>
							    <input type="text" class="form-control" id="price" name="price" placeholder="${price.price}">
							  </div>
							  <button type="submit" class="btn btn-default">Changer le prix</button>
                    	</form>
                    </li>
                </c:forEach>
                </ul>
            </div>
        </div>
    </div>
  </body>
</html>