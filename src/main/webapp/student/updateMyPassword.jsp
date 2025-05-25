<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경폼</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>

<!-- 1. 현재 비밀번호 일치 여부 확인 -->
<!-- 2. 새 비밀번호 입력받기 -->
<!-- 3. 확인 비밀번호 입력받기 -->
<!-- 4. 비밀번호 변경 처리: 백엔드(전용서블릿 생성 및 데이터접근 객체 업데이트 쿼리 메서드 추가) -->
<form action="myInfoAction" method="post">
	<input type="hidden" name="action" value="updatePhoneNumber" />
	<input type="password" name="password" />
	<input type="submit" value="변경">
</form>

</body>
</html>