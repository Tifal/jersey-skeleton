<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <jsp:include page="/layout/head.jsp"/>
    <meta charset="utf-8">
    <title>Retraits</title>
  </head>
  <body>
    <jsp:include page="/layout/navbar.jsp"/>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <h1>Retraits</h1>
                <ul class="list-group">
                <form class="form-inline"  action="/html/relais/" method="post">
            		 <div class="form-group">
					    <label for="name">Nom :</label>
					    <input type="text" class="form-control" id="name" name="name" placeholder="Nom">
					  </div>
					  <div class="form-group">
					    <label for="address">Adresse :</label>
					    <input type="text" class="form-control" id="address" name="address" placeholder="Adresse">
					  </div>
					  <button type="submit" class="btn btn-default">Ajouter</button>
            	</form>
                <c:forEach var="relais" items="${it}">
                    <li class="list-group-item">
                   		Nom du retrait : ${relais.name}<br/>
               			Adresse du retrait : ${relais.address}<br/>
               			<form action="/html/relais/${relais.name}" method="post">
               				<button type="submit">Supprimer</button>
            			<form>
                    </li>
                </c:forEach>
                </ul>
            </div>
        </div>
    </div>
  </body>
</html>