<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경폼</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>

<body>
<jsp:include page="../common/header.jsp">

<div class="container mt-5">
	<h2 class="text-center mb-4">비밀번호 변경</h2>
	
	<form action="c:url value='/studentInfo' />" method="post"></form>
		<div class="card">
			<div class="card-header bg-warning">비밀번호 변경</div>
				<div class="card-body">
					<!-- 1. 현재 비밀번호 일치 여부 확인 -->			
                	<div class="mb-3">	
						<label for="currentPassword" class="form-label">현재 비밀번호</label>
						<input type="text" class="form-control" id="currentPassword" name="currentPassword"
				 		 value="${studentInfo.userPassword}" required>
					</div>
					<!-- 2. 새 비밀번호 입력받기 -->
                	<div class="mb-3">	
						<label for="newPassword" class="form-label">변경 비밀번호</label>
						<input type="password" class="form-control" id="newPassword" name="newPassword" required>
					</div>			
					<!-- 3. 확인 비밀번호 입력받기 -->
                	<div class="mb-3">	
						<label for="confirmPassword" class="form-label">확인 비밀번호</label>
						<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
					</div>
				</div>
			</div>

			<!-- 4. 비밀번호 변경 처리: 백엔드(전용서블릿 생성 및 데이터접근 객체 업데이트 쿼리 메서드 추가) -->
			<div class="text-center mt-4">
				<button type="submit" class="btn btn-primary">변경</button>
				<a href="<c:url value='/studentInfo' />" class="btn btn-secondary">취소</a>
			</div>
</div>

<jsp:include page="../common/footer.jsp">
</body>
</html>

<!-- [초기 코드]
<form action="myInfoAction" method="post">
	<input type="hidden" name="action" value="updatePhoneNumber" />
	<input type="password" name="password" />
	<input type="submit" value="변경">
</form>
 -->