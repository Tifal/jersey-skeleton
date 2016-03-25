var indexCourant=0;
var loginCourant=undefined;
var idCourant=0;

function getUser(name) {
	getUserGeneric(name, "v1/user/");
}

function getUserBdd(name) {
	getUserGeneric(name, "v1/userdb/");
}

function getUserGeneric(name, url) {
	$.getJSON(url + name, function(data) {
		afficheUser(data);
	});
}

function getForAll() {
	getSecure("v1/secure/forall");
}

function getForLogged() {
	getSecure("v1/secure/onlylogged");
}

function getByAnnotation() {
	getSecure("v1/secure/byannotation");
}

 function getSecure(url) {
 if($("#userlogin").val() != "") {
     $.ajax
     ({
       type: "GET",
       url: url,
       dataType: 'json',
       beforeSend : function(req) {
        req.setRequestHeader("Authorization", "Basic " + btoa($("#userlogin").val() + ":" + $("#passwdlogin").val()));
       },
       success: function (data){
        afficheUser(data)
       },
       error : function(jqXHR, textStatus, errorThrown) {
       			alert('error: ' + textStatus);
       		}
     });
     } else {
     $.getJSON(url, function(data) {
     	    afficheUser(data);
        });
     }
 }

function postUser(name, alias) {
    postUserGeneric(name, alias, "", "v1/user/");
}

function postUser1(name, pwd){

	//console.log("test");
	
	$.getJSON("v1/userdb/", function(data) {
		
		
		// afficheListUsers(data)
		
		if(existeLog(data, name)){
			alert("Login existe deja");
		}else{
    		postUserGeneric(name, name, pwd, "v1/userdb/");
		}
		
		
	});
	
	
}

function postUser2(name, pwd){

	// console.log("test pwd : "+pwd);
	
	$.getJSON("v1/userdb/", function(data) {
		
		
		// afficheListUsers(data)
		
		if(existeLog(data, name) && true /* existePwd(data, pwd)*/ ){
			alert("Vous êtes connecté");
			loginCourant=data[indexCourant].name;
			idCourant=data[indexCourant].id;
			$("#order").show();
			$("#login").hide();
			$("#create-user").hide();
			// alert(" : "+loginCourant);
		}else{
    		alert("Erreur de login ou mot de passe");
    		indexCourant=undefined;
		}
		
		
	});
	
	
}

function postUserBdd(name, alias, pwd) {
    postUserGeneric(name, alias, pwd, "v1/userdb/");
}

function postUserGeneric(name, alias, pwd, url) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		dataType : "json",
		data : JSON.stringify({
			"name" : name,
			"alias" : alias,
			"password" : pwd,
			"id" : 0
		}),
		success : function(data, textStatus, jqXHR) {
			afficheUser(data);		
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('postUser error: ' + textStatus);
		}
	});
}

function listUsers() {
    listUsersGeneric("v1/user/");
}

function listUsersBdd() {
    listUsersGeneric("v1/userdb/");
}

function listUsersGeneric(url) {
	$.getJSON(url, function(data) {
		afficheListUsers(data)
	});
}

function afficheUser(data) {
	console.log(data);
	$("#reponse").html(data.id + " : <b>" + data.alias + "</b> (" + data.name + ")");
}

function afficheListUsers(data) {
	var html = '<ul>';
	var index = 0;
	for (index = 0; index < data.length; ++index) {
		html = html + "<li>"+ data[index].name + "</li>";
	}
	html = html + "</ul>";
	$("#reponse").html(html);
}

function existeLog(data, name) {
	var html = '<ul>';
	var index = 0;
	for (index = 0; index < data.length; ++index) {
		html = html + "<li>"+ data[index].name + "</li>";
		
		if(name === data[index].name){
			indexCourant=index;
			// console.log("indexCourant: "+indexCourant+" index: "+index);
			return true;
		}
		
	}
	html = html + "</ul>";
	$("#reponse").html(html);
	
	return false;
}

function existePwd(data, pwd) {
	var html = '<ul>';
	var index = 0;
	
	console.log(data[40]);
	
	for (index = 0; index < data.length; ++index) {
		html = html + "<li>"+ data[index].password + "</li>";
		
		// .passwdHash
		
		console.log(" : "+data[index].password);
		
		console.log("test 2 pwd : "+pwd);
		
		if(pwd === data[index].password){
		
		console.log("va retourner true");
		
			return true;
		}
		
	}
	html = html + "</ul>";
	$("#reponse").html(html);
	
	return false;
}

function commandDBResource(addressRetrait, 
		addressLivraison, dateRetrait, dateLivraison, price){

	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v1/commanddb/",
		dataType : "json",
		beforeSend: function(xhr) { xhr.setRequestHeader("Authorization", "Basic " + btoa( $("#login [name='login']").val()+ ":"+$("#login [name='pwd']").val()));},
		data : JSON.stringify({
			"id" : 0,
			"userid" : idCourant,
			"addressRetrait" : addressRetrait,
			"addressLivraison" : addressLivraison,
			"dateRetrait" : dateRetrait,
			"dateLivraison" : dateLivraison,
			"price" : price
		}),
		success : function(data, textStatus, jqXHR) {
			alert("ok");
			console.log(data.id +" - "+ data.userid+" - "+data.addressRetrait+" - "+data.addressLivraison+" - "+data.dateRetrait+" - "+data.dateLivraison+" - "+data.price);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('error: ' + textStatus);
		}
	});
	
}

function recupPriceBdd(iPrice, iPositionPrixBdd){
	
	$.ajax({
		type: "GET",
        contentType : 'application/json',
        url: "v1/pricedb",
        dataType: 'json',
        success: function (data){
        	
        	if(iPositionPrixBdd<=5){
        		$("#order-form #price"+iPrice).val(data[iPositionPrixBdd].price+" /kg TTC");        		
        	}else{
        		$("#order-form #price"+iPrice).val(data[iPositionPrixBdd].price+" /à l'unité TTC");        		

        	}
        	
        	/*
        	$(".username-value").html(cleanInput(user) + ' <span class="caret">');
        	showMenu(".main");
        	*/
        },
        error : function(jqXHR, textStatus, errorThrown) {
       		showError("error");
       	}
     });
	
}
