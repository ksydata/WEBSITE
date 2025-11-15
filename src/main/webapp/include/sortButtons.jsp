<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="${pageURL}?sort=asc&field=${field}${paramStr}"
   		class="btn btn-sm btn-light ms-1" title="오름차순 정렬">▲</a>

	<a href="${pageURL}?sort=desc&field=${field}${paramStr}"
	   class="btn btn-sm btn-light ms-1" title="내림차순 정렬">▼</a>
</body>
</html>