<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>나의 개인정보</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">    
</head>

<body>
<!-- 공통 상단 메뉴 -->
<jsp:include page="../common/header.jsp" />

<div class="container mt-5">
    <h2 class="text-center mb-4">나의 개인정보</h2>
    
	<!-- StudentDTO 객체인 studentInfo가 공백일 경우 알림 호출 -->
	<c:if test="${empty adminInfo}">
    	<script>
        	alert("개인정보를 찾을 수 없습니다.");
        	window.location.href = "<c:url value='admin/main.jsp' />";
    	</script>
	</c:if>

	<!-- 세션에 저장된 StudentDTO 객체인 studentInfo를 불러와 만든 나의 개인정보 조회 카드 -->

    <!-- 기본 정보 카드(읽기 전용) -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">기본 정보</div>
        <div class="card-body">
            <p><strong>학번:</strong> ${adminInfo.userID}</p>
            <p><strong>이름:</strong> ${adminInfo.name}</p>
            <p><strong>주민등록번호:</strong> ${adminInfo.residentNumber}</p>
            <p><strong>상태:</strong> ${adminInfo.status}</p>
        </div>
    </div>

    <!-- 수정 가능한 정보 카드 -->
    <div class="card mb-4">
        <div class="card-header bg-secondary text-white">수정 가능한 정보</div>
        <div class="card-body">
            <p><strong>휴대전화번호:</strong> ${adminInfo.phoneNumber}</p>
            <p><strong>이메일:</strong> ${adminInfo.email}</p>
            <p><strong>주소:</strong> ${adminInfo.address}</p>
        </div>
    </div>

    <!-- 버튼 -->
    <div class="text-center">
        <a href="<c:url value='admin/updateMyInfo.jsp' />" class="btn btn-warning">개인정보 수정</a>
        <a href="<c:url value='admin/updateMyPassword.jsp' />" class="btn btn-warning">비밀번호 변경</a>        
    </div>
</div>
  
<!-- 하단 영역의 메인 페이지로 돌아가는 링크 -->
<br>
<a href="<c:url value='admin/main.jsp' />">메인으로 돌아가기</a>
<!-- 공통 푸터 -->
<jsp:include page="../common/footer.jsp" />
</body>

</html>