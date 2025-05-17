<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--HTML 5 문서유형 선언--%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<%-- 브라우저 탭에 표시될 웹사이트 제목 --%>
	<title>학생 메인 페이지</title>
	<%-- 문자 인코딩 설정 (Unicode Transformation Format–8bit) --%>
	<meta charset="UTF-8">
	<!-- 부트스트랩 스타일 시트 연결 href="./css/bootstrap.min.css?ver=1" -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
</head>

<body>

<div class="container mt-5 text-center">
	<h1>${sessionScope.userName}님 (${sessionScope.userID}) 환영합니다.</h1>
	<div class="mt-4">
		<a href="<c:url value='/studentInfo' />" class="btn btn-primary m-2">나의 개인정보 조회/수정</a>
		<%-- 
		<a href="student/myPersonalInfo.jsp" class="btn btn-primary m-2">나의 개인정보 조회/수정</a> 
		--%>
		<a href="<c:url value='/studentRecord' />" class="btn btn-primary m-2">나의 학사정보 조회</a>
		<a href="student/common/postlist.jsp" class="btn btn-primary m-2">공지사항</a>
	</div>	
</div>

<%-- 필수 자바스크립트 라이브러리들 추가 --%>
<script src="../js/jquery.min.js"></script>
<script src="../js/popper.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
</body>

</html>