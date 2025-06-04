<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경폼</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>

<body>
<jsp:include page="../common/header.jsp" />

<div class="container mt-5">
	<h2 class="text-center mb-4">비밀번호 변경</h2>
	
	<!-- 비밀번호 변경 실패 시 에러 메시지 출력 -->
	<c:if test="${not empty error}">
		<div class="alert alert-danger text-center">${error}</div>
	</c:if>
	<!-- 비밀번호 변경 성공 시 메시지 출력 -->
	<c:if test="${not empty success}">
		<div class="alert alert-success text-center">${success}</div>
	</c:if>

	<form action="<c:url value='/professorPassword' />" method="post">
		<div class="card">
			<div class="card-header bg-warning">비밀번호 변경</div>
				<div class="card-body">
					<!-- 1. 현재 비밀번호 일치 여부 확인 -->			
                	<div class="mb-3">	
						<label for="currentPassword" class="form-label">현재 비밀번호</label>
						<input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
						<!-- value="${studentInfo.userPassword}" 보안상 취약한 비밀번호 노출 위험  -->
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
				<a href="<c:url value='/professorInfo' />" class="btn btn-secondary">취소</a>
			</div>
	</form>
</div>

<jsp:include page="../common/footer.jsp" />
</body>
</html>