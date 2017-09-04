<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Login성공
<form action="<c:url value='/logout'/>" method="POST"> 
	<input type="text" name="${_csrf.parameterName}" value="${_csrf.token}" />
	<button type="submit">LOG OUT</button> 
</form>
</body>
</html>