<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*,java.io.*;"%>
<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>Commande numéro ${it.id}</title>
    <jsp:include page="/layout/head.jsp"/>
  </head>
  <body>
    <jsp:include page="/layout/navbar.jsp"/>
    <div class="container">
      <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">Détail de la commande numéro ${it.id}</h3>
              </div>
              <div class="panel-body">
                Id : ${it.id}<br/>
                userid : <a href="../user/${it.userid}">${it.userid}</a><br/>
                adresse de retrait : ${it.addressRetrait} <br/>
                date de retrait : ${it.dateRetrait} <br/>
                adresse de livraison : ${it.addressLivraison} <br />
                date de livraison : ${it.dateLivraison} <br/>
                prix de la commande : ${it.price} € <br/><br />
 				detail : 
 				<ul class="list-group">
 					<br />${it.info}
 				 </ul>
              </div>
            </div>
        </div>
      </div>
    </div>
  </body>
</html>
