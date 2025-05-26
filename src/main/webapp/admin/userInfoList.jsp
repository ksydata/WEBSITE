<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*" %>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원 목록</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<link rel="stylesheet" href="./css/custom.css">
</head>

<body> 
<div class="container mt-5">
	<h2 class="text-center mb-4">👥 회원 리스트</h2>
	
	<!-- 회원 관련 메시지 (예: 삭제 완료, 등록 완료 등) -->
	<c:if test="${not empty sessionScope.flashMessage}">
	    <script>
	        alert("${sessionScope.flashMessage}");
	    </script>
	    <c:remove var="flashMessage" scope="session" />
	</c:if>

	<!-- 역할 필터 라디오 버튼 -->
	<form method="get" action="adminUserList" class="mb-4 text-center">
	    <div class="btn-group" role="group" aria-label="역할 필터">
	        <input type="radio" class="btn-check" name="role" value="전체" id="role-all"
	            ${selectedRole == null || selectedRole == '전체' ? 'checked' : ''} onchange="this.form.submit()">
	        <label class="btn btn-outline-primary" for="role-all">전체</label>

	        <input type="radio" class="btn-check" name="role" value="학생" id="role-student"
	            ${selectedRole == '학생' ? 'checked' : ''} onchange="this.form.submit()">
	        <label class="btn btn-outline-primary" for="role-student">학생</label>

	        <input type="radio" class="btn-check" name="role" value="교수" id="role-professor"
	            ${selectedRole == '교수' ? 'checked' : ''} onchange="this.form.submit()">
	        <label class="btn btn-outline-primary" for="role-professor">교수</label>

	        <input type="radio" class="btn-check" name="role" value="교직원" id="role-staff"
	            ${selectedRole == '교직원' ? 'checked' : ''} onchange="this.form.submit()">
	        <label class="btn btn-outline-primary" for="role-staff">교직원</label>

	        <input type="radio" class="btn-check" name="role" value="관리자" id="role-admin"
	            ${selectedRole == '관리자' ? 'checked' : ''} onchange="this.form.submit()">
	        <label class="btn btn-outline-primary" for="role-admin">관리자</label>
	    </div>
	</form>

	<table class="table table-hover">
	    <thead class="table-dark">
	        <tr>
	            <th>회원 ID</th>
	            <th>이름</th>
	            <th>이메일</th>
	             <%-- <th>가입일</th> --%>
	            <th>권한</th>
	        </tr>
	    </thead>
	    <tbody>
	        <c:forEach var="user" items="${userList}">
	            <tr>
	                <td>${user.userID}</td>
	                <td>${user.name}</td>
	                <td>${user.email}</td>
	                <%-- <td>
	                    <fmt:formatDate value="${user.registerDate}" pattern="yyyy-MM-dd HH:mm" timeZone="Asia/Seoul" />
	                </td> --%>
	                <td>${user.userRole}</td>
	            </tr>
	        </c:forEach>
	    </tbody>
	</table>
</div>
</body>
</html>

<%@ include file="/common/footer.jsp" %>