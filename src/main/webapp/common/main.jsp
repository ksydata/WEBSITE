<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%-- 브라우저 탭에 표시될 웹사이트 제목 --%>
	<title>메인 페이지</title>
	<%-- 문자 인코딩 설정 (Unicode Transformation Format–8bit) --%>
	<meta charset="UTF-8">
	<!-- 부트스트랩 스타일 시트 연결 href="./css/bootstrap.min.css?ver=1" -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
</head>
<body>
<!-- 공통 상단 메뉴 -->
<jsp:include page="../include/header.jsp" />

<div class="container mt-5 text-center">
	<h1>${sessionScope.userName}님 (${sessionScope.userID}) 환영합니다.</h1>
	<div class="mt-4">
		<!-- 공통 메뉴 1 -->
		<!-- 현재는 '나의 개인정보 조회/수정'이 권한별로 다른 메뉴이지만, 해당 메뉴를 common/myPersonalInfo.jsp 로 통합함 -->
		<a href="<c:url value='/userInfo' />" class="btn btn-primary m-2">
			나의 개인정보 조회/수정
		</a>

		<!-- ========================= -->
		<!--  권한별 조건부 메뉴 영역   -->
		<!-- ========================= -->
		<c:choose>

			<!-- 학생 권한 -->
			<c:when test="${sessionScope.userRole.isROLE_001()}">
				<a href="<c:url value='/studentRecord' />" class="btn btn-success m-2">
					나의 학사정보 조회
				</a>
			</c:when>
			
			<%-- [AS-IS]
			<c:when test="${sessionScope.userRole eq '학생'}">
				<a href="<c:url value='/studentRecord' />" class="btn btn-success m-2">
					나의 학사정보 조회
				</a>
			</c:when> --%>

			<!-- 관리자 권한 -->
			<c:when test="${sessionScope.userRole.isROLE_004()}">
				<a href="<c:url value='/adminUserList' />" class="btn btn-warning m-2">
					사용자 개인정보 조회
				</a>
				<a href="<c:url value='/studentRecordList' />" class="btn btn-warning m-2">
					사용자 학사정보 조회
				</a>
			</c:when>
			
			<%-- [AS-IS] 
			<c:when test="${sessionScope.userRole eq '관리자'}">
				<a href="<c:url value='/adminUserList' />" class="btn btn-warning m-2">
					사용자 개인정보 조회
				</a>
				<a href="<c:url value='/studentRecordList' />" class="btn btn-warning m-2">
					사용자 학사정보 조회
				</a>
			</c:when> --%>

			<!-- 교수 권한 -->
			<c:when test="${sessionScope.userRole.isROLE_002()}">
				<a href="<c:url value='/classRecord' />" class="btn btn-info m-2">
					전공생 학사정보 조회/수정
				</a>
			</c:when>
			
			<%-- [AS-IS]
			<c:when test="${sessionScope.userRole eq '교수'}">
				<a href="<c:url value='/classRecord' />" class="btn btn-info m-2">
					전공생 학사정보 조회/수정
				</a>
			</c:when> --%>

			<!-- 교직원 권한 -->
			<c:when test="${sessionScope.userRole.isROLE_003()}">
				<a href="studentPersonalInfo.jsp" class="btn btn-dark m-2">
					학생 개인정보 조회
				</a>
			</c:when>
			
			<%-- [AS-IS] 
			<c:when test="${sessionScope.userRole eq '교직원'}">
				<a href="studentPersonalInfo.jsp" class="btn btn-dark m-2">
					학생 개인정보 조회
				</a>
			</c:when> --%>

		</c:choose>
		<!-- 권한별 메뉴 끝 -->

		<!-- 공통 메뉴 2 -->
		<a href="<c:url value='/board' />" class="btn btn-primary m-2">
			공지사항
		</a>

	</div>	
</div>

<%-- 필수 자바스크립트 라이브러리들 추가 --%>
<script src="../js/jquery.min.js"></script>
<script src="../js/popper.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<!-- 공통 푸터 -->
<jsp:include page="../include/footer.jsp" />

</body>
</html>