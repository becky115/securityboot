<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
	<title>Home</title>
	<meta http-equiv="Content-Type" content="text/html; charsetUTF-8">
</head>
<body>
<h1>
	Hello world!  
</h1>
하하핳하
<P>  The time on the server is ${serverTime}.@ </P>

<sec:authentication var="user" property="principal" />
<P>${user}</P>
<form action="<c:url value='/logout'/>" method="POST"> 
	<input type="text" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<button type="submit">LOG OUT</button> 
</form>
	<button type="button" onclick="location.href='<c:url value='/test/index.do'/>'">test page 이동</button>
	<button type="button" onclick="location.href='<c:url value='/test2/index.do'/>'">test2 page 이동</button>
</body>
</html>
