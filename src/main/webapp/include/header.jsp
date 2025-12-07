<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
    header.jsp
    - 로그인한 사용자(userID, userRole)의 역할(role)에 따라
      각 사용자 유형별(학생/교수/교직원/관리자) 메뉴를 다르게 출력하는 공통 상단 메뉴 파일
    - 모든 main.jsp에서 공통으로 include하여 사용
    - 페이지 이동/권한 분기는 IndexServlet이 담당하고, 이 파일은 단순히 메뉴만 표시하는 용도로 사용
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
	    <%-- 메인으로 이동하는 링크: 항상 IndexServlet(/index)으로 이동 --%>
        <a class="navbar-brand" href="<c:url value='/index'/>">
        	학사관리 시스템
        </a>
	
		<%-- 모바일 메뉴 토글 버튼(화면 크기가 작아질 때 메뉴 축소/확장) --%>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<%-- 네비게이션 항목들 --%>		
		<div id="navbar" class="collapse navbar-collapse">
			<%-- 왼쪽 메뉴: 목록(list)를 가지는 요소인 ul 태그 --%>	
			<ul class="navbar-nav mr-auto">
			
				<%-- 1. 공통 메뉴 --%>
		        <li class="nav-item">
		            <a class="nav-link" href="<c:url value='/common/notice'/>">공지사항</a>
		        </li>
		        <li class="nav-item">
		            <a class="nav-link" href="<c:url value='/common/info'/>">개인정보</a>
		        </li>
		
		
		        <%-- 2. 역할 기반 권한: 학생 메뉴 --%>
		        <c:if test="${sessionScope.userRole eq 'student'}">
		            <li class="nav-item">
		                <a class="nav-link" href="<c:url value='/student/myAcademicRecord'/>">나의 학사정보</a>
		            </li>
		        </c:if>
		
		        <%-- 3. 역할 기반 권한: 교수 메뉴 --%>
		        <c:if test="${sessionScope.userRole eq 'professor'}">
		            <li class="nav-item">
		                <a class="nav-link" href="<c:url value='/professor/classAcademicRecord'/>">전공생 학사정보 관리</a>
		            </li>
		        </c:if>
		
		        <%-- 4. 역할 기반 권한: 관리자 메뉴 --%>
		        <c:if test="${sessionScope.userRole eq 'admin'}">
		            <li class="nav-item">
		                <a class="nav-link" href="<c:url value='/admin/userPersonalInfo'/>">관리자 개인정보 관리</a>
						<a class="nav-link" href="<c:url value='/admin/userAcademicRecord'/>">관리자 학사정보 관리</a>    
		            </li>
		        </c:if>
			
				
			</ul>
			<%-- 오른쪽 버튼: 로그아웃 링크 --%>
			<ul class="navbar-nav">
				<!-- 로그아웃 링크를 nav-item 내부에 배치 -->
				<li class="nav-item">
					<a href="<%=request.getContextPath()%>/LogoutServlet" class="btn btn-danger">로그아웃</a>
					<%-- <li class="nav-item"><a class="nav-link" href="./common/logout.jsp">로그아웃</a> --%>					
				</li>
			</ul>
		</div>
    </nav>
</body>

</html>

<%-- [AS-IS] 사용자 권한에 따라 메뉴 표시 [TO-BE] IndexServlet --%>
<%-- 
<body>
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
</body>		
--%>
			