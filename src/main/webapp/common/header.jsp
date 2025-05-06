<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
--%>
<%--HTML 5 문서유형 선언--%>
<!DOCTYPE html>
<html>

<head>
	<%-- 브라우저 탭에 표시될 웹사이트 제목 --%>
	<title>눈송여자대학교 학사관리 시스템</title>
	<%-- 문자 인코딩 설정 (Unicode Transformation Format–8bit) --%>
	<meta charset="UTF-8">
	<%-- 모바일 웹 반응형 지원(뷰 포트에 맞는 설정 추가) --%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- 부트스트랩 스타일 시트 연결 href="./css/bootstrap.min.css?ver=1" -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<!-- 사용자 정의(커스텀) 스타일 시트 연결 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>

<body>
	<%-- 네비게이션 바(반응형 디자인을 위한 설정, 하얀색 배경) 추가 --%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">학사관리 시스템</a>
		<%-- 모바일 메뉴 토글 버튼(화면 크기가 작아질 때 메뉴 축소/확장) --%>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<%-- 사용자 권한에 따라 메뉴 표시 --%>
		<%-- 
		<c:choose>
			<c:when test="${userRole == '학생'}">
				<li class="nav-item">
					<a class="nav-link" href="student/myPersonalInfo.jsp">나의 개인정보 조회</a>
					<a class="nav-link" href="student/myAcademicRecord.jsp">나의 학사정보 조회</a>					
				</li>
			</c:when>
			<c:when test="${userRole == '교수'}">
				<li class="nav-item">
					<a class="nav-link" href="professor/myPersonalInfo.jsp">나의 개인정보 조회</a>
					<a class="nav-link" href="professor/classAcademicRecord.jsp">수강생 학사정보 조회</a>					
				</li>
			</c:when>
			<c:when test="${userRole == '교직원'}">
				<li class="nav-item">
					<a class="nav-link" href="employee/myPersonalInfo.jsp">나의 개인정보 조회</a>
					<a class="nav-link" href="employee/studentPersonalInfo.jsp">학생 개인정보 조회</a>					
				</li>
			</c:when>
			<c:when test="${userRole == '관리자'}">
				<li class="nav-item">
					<a class="nav-link" href="admin/myPersonalInfo.jsp">나의 개인정보 조회</a>
					<a class="nav-link" href="admin/userPersonalInfo.jsp">사용자 개인정보 조회</a>
					<a class="nav-link" href="admin/userAcademicRecord.jsp">사용자 학사정보 조회</a>														
				</li>
			</c:when>						
		</c:choose>
		--%>
			
		<%-- 네비게이션 항목들 --%>		
		<div id="navbar" class="collapse navbar-collapse">
			<%-- 목록(list)를 가지는 요소인 ul 태그 --%>	
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<!-- 로그아웃 링크를 nav-item 내부에 배치 -->
					<a href="<%=request.getContextPath()%>/LogoutServlet" class="btn btn-danger">로그아웃</a>
				</li>
				<%-- <li class="nav-item"><a class="nav-link" href="./common/logout.jsp">로그아웃</a> --%>
			</ul>
		</div>
    </nav>
</body>

</html>