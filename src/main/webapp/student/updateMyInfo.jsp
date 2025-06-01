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

	<form id="infoForm" action="<c:url value='/studentInfo' />" method="post" onsubmit="return combineInputs()">
	<!-- <form action="<c:url value='/studentInfo' />" method="post"> -->
    <!-- <form action="${pageContext.request.contextPath}/studentInfo" method="post"> -->
        <div class="card">
            <div class="card-header bg-warning">
                수정 가능한 정보
            </div>
            <div class="card-body">
                <!-- 휴대전화번호 분리 입력 -->
                <div class="mb-3">
                    <label for="phoneNumber" class="form-label">휴대전화번호</label>
                    <div class="d-flex gap-2 aligin=items-center">
	                    <input type="text" class="form-control" id="phoneNum1st" maxlength="3" placeholder="010" required>
	                    <input type="text" class="form-control" id="phoneNum2nd" maxlength="4" placeholder="1234" required>
	                    <input type="text" class="form-control" id="phoneNum3rd" maxlength="4" placeholder="5678" required>
	                </div>
                    <input type="hidden" id="phoneNumber" name="phoneNumber" 
                     	   value="${studentInfo.phoneNumber}">                     
                </div>
	            <!-- 이메일 분리 입력 -->            
                <div class="mb-3">
                    <label for="email" class="form-label">이메일</label>
	                <div class="d-flex gap-2 align-items-center">
	                	<input type="text" class="form-control" id="emailId" placeholder="아이디 (학번/사번)" required>
	                    <span>@</span>
	                    <input type="text" class="form-control" id="emailDomain" placeholder="univ.com" required>
	                </div>
                    <input type="hidden" id="email" name="email"
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

<script>
	function combineInputs() {
		// 입력값을 서버로 보낼 때는 하나로 합치기 위한 자바스크립트 함수 
		const phoneNum1st = document.getElementById("phoneNum1st").value.trim();
		const phoneNum2nd = document.getElementById("phoneNum2nd").value.trim();
		const phoneNum3rd = document.getElementById("phoneNum3rd").value.trim();
		document.getElementById("phoneNumber").value = phoneNum1st+"-"+phoneNum2nd+"-"+phoneNum3rd;
		
	    const emailId = document.getElementById("emailId").value.trim();
	    const emailDomain = document.getElementById("emailDomain").value.trim();
	    document.getElementById("email").value = emailId + "@" + emailDomain;
	    
	    return true;
	  }
</script>

<jsp:include page="../common/footer.jsp" />
</body>

</html>