<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--HTML 5 문서유형 선언--%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>단과대학 전공생 학사정보 조회</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">    
</head>

<body>
<!-- 공통 상단 메뉴 -->
<jsp:include page="../common/header.jsp" />

<h2>전공생 학사정보</h2>
<table border="1">
    <tr>
        <th>이름</th>
        <th>단과대학</th>        
        <th>전공</th>
        <th>과목</th>
        <th>등급</th>
        <th>평점</th>
        <th>학기</th>
    </tr>
    <c:forEach var="c" items="${classRecordsList}">
        <tr>
            <td>${classRecordsList.name}</td>
            <td>${classRecordsList.college}</td>            
            <td>${classRecordsList.major}</td>
            <td>${classRecordsList.courseName}</td>
            <td>${classRecordsList.grade}</td>
            <td>${classRecordsList.gradePoint}</td>
            <td>${classRecordsList.semester}</td>
        </tr>
    </c:forEach>
</table>

<!-- 하단 영역의 메인 페이지로 돌아가는 링크 -->
<br>
<a href="<c:url value='professor/main.jsp' />">메인으로 돌아가기</a>
<!-- 공통 푸터 -->
<jsp:include page="../common/footer.jsp" />
</body>
</html>
