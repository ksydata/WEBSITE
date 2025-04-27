<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	// 세션에서 로그인된 사용자 ID를 가져옴
	String userID = (String) session.getAttribute("userID");

	// 로그인되어있지 않지만 로그인 페이지로 리다이렉트
	if (userID == null) {
		// 로그인 페이지로 이동
		response.sendRedirect("login.jsp");
		// 이후 JSP 실행 중단
		return;
	}
%>
<%--HTML 5 문서유형 선언--%>
<!DOCTYPE html>
<html>

<head>
	<%-- 브라우저 탭에 표시될 웹사이트 제목 --%>
	<title>학사관리 시스템</title>
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
		<%-- 네비게이션 항목들 --%>		
		<div id="navbar" class="collapse navbar-collapse">
			<%-- 목록(list)를 가지는 요소인 ul 태그 --%>	
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="logout.jsp">로그아웃</a>
			</ul>
		</div>
    </nav>

    <%-- 푸터(웹사이트 최하단 영역) --%>
    <%-- 어두운 배경(dark background)에 흰색 텍스트로 푸터 설정 --%>
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