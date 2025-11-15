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
<jsp:include page="../include/header.jsp" />


<div class="container mt-5">
    <h2 class="text-center mb-4">개인정보 수정</h2>

	<form id="infoForm" action="<c:url value='/professorInfo' />" method="post" onsubmit="return combineInputs()">
	<!-- <form action="<c:url value='/professorInfo' />" method="post"> -->
    <!-- <form action="${pageContext.request.contextPath}/professorInfo" method="post"> -->
        <div class="card">
            <div class="card-header bg-warning">
                수정 가능한 정보
            </div>
            <div class="card-body">
                <!-- 휴대전화번호 분리 입력 -->
                <div class="mb-3">
                    <label for="phoneNumber" class="form-label">휴대전화번호</label>
                    <div class="d-flex gap-2 aligin=items-center">
	                    <input type="text" class="form-control" id="phoneNum1st" maxlength="3" placeholder="010">
	                    <span>-</span>	                    
	                    <input type="text" class="form-control" id="phoneNum2nd" maxlength="4" placeholder="1234">
	                    <span>-</span>	                    
	                    <input type="text" class="form-control" id="phoneNum3rd" maxlength="4" placeholder="5678">
	                </div>
                    <input type="hidden" id="phoneNumber" name="phoneNumber" 
                     	   value="${professorInfo.phoneNumber}">                     
                </div>
                <!-- 사무실 전화번호 분리 입력 -->
                <div class="mb-3">
                    <label for="officeNumber" class="form-label">사무실 전화번호</label>
                    <div class="d-flex gap-2 aligin=items-center">
	                    <input type="text" class="form-control" id="officeNum1st" maxlength="3" placeholder="02">
	                    <span>-</span>	                    
	                    <input type="text" class="form-control" id="officeNum2nd" maxlength="4" placeholder="1234">
	                    <span>-</span>	                    
	                    <input type="text" class="form-control" id="officeNum3rd" maxlength="4" placeholder="5678/#NA">
	                </div>
                    <input type="hidden" id="officeNumber" name="officeNumber" 
                     	   value="${professorInfo.officeNumber}">                     
                </div>                
	            <!-- 이메일 분리 입력 -->            
                <div class="mb-3">
                    <label for="email" class="form-label">이메일</label>
	                <div class="d-flex gap-2 align-items-center">
	                	<input type="text" class="form-control" id="emailId" placeholder="아이디 (학번/사번)">
	                    <span>@</span>
	                    <input type="text" class="form-control" id="emailDomain" placeholder="univ.com">
	                </div>
                    <input type="hidden" id="email" name="email"
                           value="${professorInfo.email}" required>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">주소</label>
                    <input type="text" class="form-control" id="address" name="address"
                           value="${professorInfo.address}" required>
                </div>
            </div>
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-primary">저장</button>
			<a href="<c:url value='/professorInfo' />" class="btn btn-secondary">취소</a>
            <!-- <a href="${pageContext.request.contextPath}/professorInfo" class="btn btn-secondary">취소</a> -->
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
		
		// 사무실 전화번호에 대해 내선번호/라인번호만 받는 경우에는 마지막 3번째 입력값 공백 허용
		const officeNum1st = document.getElementById("officeNum1st").value.trim();
		const officeNum2nd = document.getElementById("officeNum2nd").value.trim();
		// [변수] let 변수를 선언만 하고 <input> 태그를 통한 값이 할당되지 않아 공백이면 
		let officeNum3rd = document.getElementById("officeNum3rd").value.trim();
		// [조건부(삼항) 연산자] 조건? 참일 때 값(앞에 대시 붙이고 끝자리 번호) : 거짓일 때 값("")
	    officeNum3rd = officeNum3rd ? "-"+officeNum3rd : "";
		// [상수] const officeNum3rd = document.getElementById("officeNum3rd").value.trim();
		document.getElementById("officeNumber").value = officeNum1st+"-"+officeNum2nd + officeNum3rd;

		
	    const emailId = document.getElementById("emailId").value.trim();
	    const emailDomain = document.getElementById("emailDomain").value.trim();
	    document.getElementById("email").value = emailId + "@" + emailDomain;
	    
	    return true;
	  }
</script>

<jsp:include page="../include/footer.jsp" />
</body>

</html>