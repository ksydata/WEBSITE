<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>개인정보 수정폼</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>

<body>
<jsp:include page="../common/header.jsp" />


<div class="container mt-5">
    <h2 class="text-center mb-4">개인정보 수정</h2>

	<form action="<c:url value='/studentInfo' />" method="post">
    <!-- <form action="${pageContext.request.contextPath}/studentInfo" method="post"> -->
        <div class="card">
            <div class="card-header bg-warning">
                수정 가능한 정보
            </div>
            <div class="card-body">
                <div class="mb-3">
                    <label for="phoneNumber" class="form-label">휴대전화번호</label>
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                           value="${studentInfo.phoneNumber}" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">이메일</label>
                    <input type="email" class="form-control" id="email" name="email"
                           value="${studentInfo.email}" required>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">주소</label>
                    <input type="text" class="form-control" id="address" name="address"
                           value="${studentInfo.address}" required>
                </div>
            </div>
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-primary">저장</button>
			<a href="<c:url value='/studentInfo' />" class="btn btn-secondary">취소</a>
            <!-- <a href="${pageContext.request.contextPath}/studentInfo" class="btn btn-secondary">취소</a> -->
        </div>
    </form>
</div>

<jsp:include page="../common/footer.jsp" />
</body>

</html>