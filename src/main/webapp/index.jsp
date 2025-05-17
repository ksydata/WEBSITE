<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/header.jsp" %>

<main class="container mt-5">
	<h1>눈송여자대학교 학사관리 시스템에 오신 것을 환영합니다</h1>

	<!-- 
	디버깅용 세션 값 출력 
	<p>userID: ${sessionScope.userID}</p>
	<p>userName: ${sessionScope.userName}</p>
	<p>userRole: ${sessionScope.userRole}</p> 
	-->
</main>

<!-- 
역할에 따라 완전히 다른 메인 페이지로 이동시키고 싶다면 
jsp가 아닌 Servlet에서 포워드(redirect) 방식이 더 적절
 -->
<c:choose>
	<c:when test="${sessionScope.userRole eq '학생'}">
		<jsp:include page="/student/main.jsp" />
	</c:when>
	<c:when test="${sessionScope.userRole eq '교수'}">
		<jsp:include page="/professor/main.jsp" />
	</c:when>
	<c:when test="${sessionScope.userRole eq '교직원'}">
		<jsp:include page="/employee/main.jsp" />
	</c:when>
	<c:when test="${sessionScope.userRole eq '관리자'}">
		<jsp:include page="/admin/main.jsp" />
	</c:when>						
</c:choose>

<%@ include file="/common/footer.jsp" %>