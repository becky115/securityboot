<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		login.init();
	});
	
	var login = {
		init: function(){
			this.initEvent();
		},
		
		initEvent: function(){
			$("#loginBtn").on("click", function(){
				console.log("param: " + $('#loginForm').serialize());
				$.ajax({
					type: 'POST',
					url: $('#loginForm').attr("action"),
					data: $('#loginForm').serialize(),
					dataType: "json",
					success: function (data) {
						console.log(data);
						//var response = JSON.parse(data);
						if (data.success == true) {
							console.info("Authentication Success!");
							location.href = "../";
						}else {
							console.error("Unable to login");
						}
					},
					error: function (data) {
						console.error("Login failure");
					}
				});
			});
		}
	}
	

</script>
<title>Login Page</title>
</head>
<body>
Login2
<%-- <form action="<c:url value='/login/success.do'/>" method="POST">  --%>
<form id="loginForm" action="<c:url value='/login'/>" method="POST"> 
	아이디 : <input type="text" id="user_id" name="username" value="admin"> 
	비밀번호 : <input type="password" id="password" name="password" value="admin">
	<input type="text" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<input type="button" id="loginBtn" value="sigin in"/>
</form>
</body>
</html>