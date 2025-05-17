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

<h2>나의 개인정보</h2>
<!-- StudentDTO 객체인 studentInfo가 공백일 경우 오류 팝업창 호출 -->
<c:if test="${empty studentInfo}">
    <script>
        alert("개인정보를 찾을 수 없습니다.");
        window.location.href = "<c:url value='student/main.jsp' />";
    </script>
</c:if>

<!-- 세션에 저장된 StudentDTO 객체인 studentInfo를 불러와 만든 나의 개인정보 조회 테이블 -->
<table>
    <tr>
        <th>학번</th>
        <td>${studentInfo.userID}</td>
    </tr>
    <tr>
        <th>이름</th>
        <td>${studentInfo.name}</td>
    </tr>
    <tr>
        <th>비밀번호</th>
        <td>${studentInfo.userPassword}</td>
    </tr>
    <tr>
        <th>주민등록번호</th>
        <td>${studentInfo.residentNumber}"</td>
    </tr>
    <!-- 개인이 직접 수정가능한 개인정보 -->
    <tr>
        <th>휴대전화번호</th>
        <td>
        	<input class="form-input" type="text" name="phoneNumber" value="${studentInfo.phoneNumber}">
        </td>
    </tr>
    <!-- 개인이 직접 수정가능한 개인정보 -->
    <tr>
        <th>이메일</th>
        <td>
        	<input class="form-input" type="text" name="email" value="${studentInfo.email}">
    	</td>
    </tr>
    <!-- 개인이 직접 수정가능한 개인정보 -->    
    <tr>
        <th>주소</th>
        <td>
        	<input class="form-input" type="text" name="address" value="${studentInfo.address}">
        </td>
    </tr>
    
    <tr>
        <th>단과대학</th>
        <td>${studentInfo.college}</td>
    </tr>
    <tr>
        <th>전공</th>
        <td>${studentInfo.major}</td>
    </tr>
    <tr>
        <th>입학년도</th>
        <td>${studentInfo.admissionYear}</td>
    </tr>
    <tr>
        <th>상태</th>
        <td>${studentInfo.status}</td>
    </tr>

</table>

<!-- 하단 영역의 메인 페이지로 돌아가는 링크 -->
<br>
<a href="<c:url value='student/main.jsp' />">메인으로 돌아가기</a>
<!-- 공통 푸터 -->
<jsp:include page="../common/footer.jsp" />
</body>

</html>