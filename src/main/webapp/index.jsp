<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--HTML 5 문서유형 선언--%>
<!DOCTYPE html>
<html>

<head>
	<%-- 브라우저 탭에 표시될 웹사이트 제목 --%>
	<title>눈송여자대학교 홈페이지</title>
	<%-- 문자 인코딩 설정 (Unicode Transformation Format–8bit) --%>
	<meta charset="UTF-8">
	<%-- 모바일 웹 반응형 지원 --%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<!-- 부트스트랩 스타일 시트 연결 -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<!-- 사용자 정의(커스텀) 스타일 시트 연결 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>

<body>
	<%-- 네비게이션 바 추가 --%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<%-- 모바일 메뉴 토글 버튼 --%>
		<%-- 네비게이션 항목들 --%>
		<%-- 드롭다운 메뉴: 회원관리 --%>
		<%-- 상단 검색 폼 --%>
    </nav>
    
	<%-- 본문 컨테이너 --%>
	<div class="container">
		<%-- 필터 및 버튼이 포함된 검색 폼 --%>
		<%-- 각각의 강의평가 카드 구성 --%>
		<%-- 1번째 강의평가 카드 --%>
		<%-- 2번째 강의평가 카드 --%>
		<%-- 3번째 강의평가 카드 --%>
		
		<%-- 페이지네이션 --%>
		<%-- 평가 등록 모달(modal) 창 --%>	
	</div>
	
    <%-- 푸터(웹사이트 최하단 영역) --%>
    <footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
    </footer>
    
    <%-- 필수 자바스크립트 라이브러리들 추가 --%>
    <script src="./js/jquery.min.js"></script>
    <script src="./js/popper.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
</body>

</html>


<%-- 
	<form action="./userJoinAction.jsp" method="post">
		<input type="text" name="userID">
		<input type="password" name="userPassword">
		<input type="submit" value="회원가입">
	</form>
--%>