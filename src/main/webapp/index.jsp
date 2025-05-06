<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/header.jsp" %>

<main class="container mt-5">
	<h1>눈송여자대학교 학사관리 시스템에 오신 것을 환영합니다</h1>
</main>

<c:choose>
	<c:when test="${userRole == '학생'}">
		<jsp:include page="/student/main.jsp">
	</c:when>
	<c:when test="${userRole == '교수'}">
		<jsp:include page="/professor/main.jsp">
	</c:when>
	<c:when test="${userRole == '교직원'}">
		<jsp:include page="/employee/main.jsp">
	</c:when>
	<c:when test="${userRole == '관리자'}">
		<jsp:include page="/admin/main.jsp">
	</c:when>						
</c:choose>

<%@ include file="/common/footer.jsp" %>