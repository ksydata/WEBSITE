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
<jsp:include page="/include/header.jsp" />

<div class="container mt-5">
    <h2 class="text-center mb-4">나의 개인정보</h2>
    
	<!-- 개인정보 수정완료 메시지 출력 -->
	<c:if test="${not empty message}">
		<div class="alert alert-success">${message}</div>
	</c:if>    
	<!-- UserInfoDTO 객체인 userInfo가 공백일 경우 경고 메시지 표시 -->
	<c:if test="${empty userInfo}">
    	<div class="alert alert-danger">개인정보를 찾을 수 없습니다.</div>
	</c:if>


	<!-- 세션에 저장된 UserInfoDTO 객체인 userInfo를 불러와 만든 나의 개인정보 조회 카드 -->
	<!-- [AS-IS] c:choose 태그를 쓰거나 공통 필드를 먼저 출력하고, 권한별로 추가 필드만 조건부로 출력 -->

    <!-- 기본 정보 카드(읽기 전용) -->
	<div class="card mb-4">
	    <div class="card-header bg-primary text-white">기본 정보</div>
	    <div class="card-body">

        <!-- ▷ 관리자 권한(admin) -->
        <c:if test="${sessionScope.role eq 'ROLE_004'}">
            <p><strong>사번:</strong> ${userInfo.userID}</p>
            <p><strong>이름:</strong> ${userInfo.name}</p>
            <p><strong>생년월일:</strong> ${userInfo.residentNumber}</p>
            <p><strong>상태:</strong> ${userInfo.status}</p>
            <p><strong>휴대전화번호:</strong> ${userInfo.phoneNumber}</p>
            <p><strong>이메일:</strong> ${userInfo.email}</p>
            <p><strong>주소:</strong> ${userInfo.address}</p>                                      
        </c:if>

        <!-- ▷ 교직원 권한(employee) -->
        <c:if test="${sessionScope.role eq 'ROLE_003'}">
            <p><strong>사번:</strong> ${userInfo.userID}</p>
            <p><strong>이름:</strong> ${userInfo.name}</p>
            <p><strong>생년월일:</strong> ${userInfo.residentNumber}</p>
            <p><strong>상태:</strong> ${userInfo.status}</p>
            <p><strong>사무실내선번호:</strong> ${userInfo.officeNumber}</p>                                  
        </c:if>
        
        <!-- ▷ 교수 권한(professor) -->
        <c:if test="${sessionScope.role eq 'ROLE_002'}">
            <p><strong>사번:</strong> ${userInfo.userID}</p>
            <p><strong>이름:</strong> ${userInfo.name}</p>
            <p><strong>생년월일:</strong> ${userInfo.residentNumber}</p>
            <p><strong>단과대학:</strong> ${userInfo.college}</p>
            <p><strong>전공:</strong> ${userInfo.major}</p>
            <p><strong>상태:</strong> ${userInfo.status}</p>
            <p><strong>사무실내선번호:</strong> ${userInfo.officeNumber}</p>            
        </c:if>

        <!-- ▷ 학생 권한(student) -->
        <c:if test="${sessionScope.role eq 'ROLE_001'}">
            <p><strong>학번:</strong> ${userInfo.userID}</p>
            <p><strong>이름:</strong> ${userInfo.name}</p>
            <p><strong>생년월일:</strong> ${userInfo.residentNumber}</p>
            <p><strong>단과대학:</strong> ${userInfo.college}</p>
            <p><strong>전공:</strong> ${userInfo.major}</p>
            <p><strong>상태:</strong> ${userInfo.status}</p>
        </c:if>
    </div>
</div>

    <!-- 수정 가능한 정보 카드 -->
    <div class="card mb-4">
        <div class="card-header bg-secondary text-white">수정 가능한 정보</div>
        <div class="card-body">
            <p><strong>휴대전화번호:</strong> ${userInfo.phoneNumber}</p>
            <p><strong>이메일:</strong> ${userInfo.email}</p>
            <p><strong>주소:</strong> ${userInfo.address}</p>
        </div>
    </div>

    <!-- 버튼 -->
    <div class="text-center">
        <a href="<c:url value='/common/info/updateMyInfo.jsp' />" class="btn btn-warning">개인정보 수정</a>
        <a href="<c:url value='/common/info/updateMyPassword.jsp' />" class="btn btn-warning">비밀번호 변경</a>        
    </div>
</div>
  
<!-- 하단 영역의 메인 페이지로 돌아가는 링크 -->
<br>
<a href="<c:url value='/main.jsp' />">메인으로 돌아가기</a>
<!-- 공통 푸터 -->
<jsp:include page="/include/footer.jsp" />
</body>

</html>