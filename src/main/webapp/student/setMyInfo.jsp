<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 휴대전화번호 수정 -->
<form action="myInfoAction" method="post">
	<input type="hidden" name="action" value="updatePhoneNumber" />
	<input type="text" name="phoneNumber" />
	<input type="submit" value="수정">
</form>
<!-- 이메일 수정 -->
<form action="myInfoAction" method="post">
	<input type="hidden" name="action" value="updateEmail" />
	<input type="text" name="email" />
	<input type="submit" value="수정">
</form>
<!-- 주소 수정 -->
<form action="myInfoAction" method="post">
	<input type="hidden" name="action" value="updateAddress" />
	<input type="text" name="address" />
	<input type="submit" value="수정">
</form>
<!-- 비밀번호 마스킹 해제 -->
<form action="myInfoAction" method="post">
	<input type="hidden" name="action" value="verifyPassword" />
	<input type="password" name="password" placeholder="" />
	<input type="submit" value="" />
	</form>
</body>
</html>