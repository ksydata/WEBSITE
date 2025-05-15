<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>나의 개인정보</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 70%; border-collapse: collapse; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
        th { background-color: #f2f2f2; }
        h2 { color: #333; }
    </style>
</head>
<body>

<h2>나의 개인정보</h2>

<c:if test="${empty studentInfo}">
    <script>
        alert('개인정보를 찾을 수 없습니다.');
        window.location.href = '<c:url value="student/main.jsp" />';
    </script>
</c:if>

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
    <tr>
        <th>휴대전화번호</th>
        <td>
        	<input class="form-input" type="text" name="phoneNumber" value="${studentInfo.phoneNumber}">
        </td>
    </tr>
    <tr>
        <th>이메일</th>
        <td>
        	<input class="form-input" type="text" name="email" value="${studentInfo.email}">
    	</td>
    </tr>
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

<br>
<a href='<c:url value="student/main.jsp" />'>메인으로 돌아가기</a>

</body>
</html>