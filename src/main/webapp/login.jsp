<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%-- UserDAO(Data Access Object)를 통해 DB 확인 후 세션 저장하는 로그인 폼 --%>
	<title>로그인 폼</title>
	<link rel="stylesheet" href="./css/bootstrap.min.css">
</head>

<body>
	<div class="container mt-5">
		<h3>로그인</h3>
		<%-- 사용자가 입력한 값을 <form> 태그를 통해 loginAction.jsp에서 처리하도록 설정 --%>
		<form action="loginAction.jsp" method="post">
			<div class="form-group">
				<label for="userID">아이디</label>
				<input type="text" class="form-control" name="userID" id="userID" required>
			</div>
			
			<div class="form-group">
				<label for="userPassword">비밀번호</label>
				<input type="text" class="form-control" name="userPassword" id="userPassword" required>
			</div>
			
			<%-- 부트스트랩 디자인을 적용한 로그인 클릭 버튼 생성(폼 데이터를 서버로 제출) --%>
			<button type="submit" class="btn btn-primary">로그인</button>
			<%-- index.jsp 페이지로 링크를 통해 이동하는 취소 버튼 생성 --%>
			<a href="./index.jsp" class="btn btn-secondary">취소</a>
		</form>
	</div>
</body>
</html>