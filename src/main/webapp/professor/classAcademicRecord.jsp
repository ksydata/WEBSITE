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

<!-- 검색 및 정렬 필터 (https://ndb796.tistory.com/43) -->

<!-- 교수의 소속 단과대학 전공생 학점 테이블 -->
<h2>전공생 학사정보</h2>
<div class="table-responsive">
<table class="table table-bordered table-hover">
    <thead class="table-primary">
    <tr>
        <th>학번</th>    
        <th>이름</th>
        <th>단과대학</th>        
        <th>전공</th>
        <th>과목</th>
        <th>등급</th>
        <th>평점</th>
        <th>수강연도</th>
        <th>학기</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="c" items="${classRecordsList}">
        <tr>
            <td>${c.userID}</td>	    
            <td>${c.name}</td>
            <td>${c.college}</td>
            <td>${c.major}</td>
            <td>${c.courseName}</td>
            <td>${c.grade}</td>
            <td>${c.gradePoint}</td>
            <td>${c.academicYear}</td>
            <td>${c.semester}</td>
        </tr>
    </c:forEach>

    <c:if test="${empty classRecordsList}">
        <tr><td colspan="9" style="text-align:center;">조회된 데이터가 없습니다.</td></tr>
    </c:if>
    </tbody>
</table>
</div>

<!-- 하단 영역의 메인 페이지로 돌아가는 링크 -->
<br>
<a href="<c:url value='professor/main.jsp' />" class="btn btn-secondary">메인으로 돌아가기</a>
<!-- 공통 푸터 -->
<jsp:include page="../common/footer.jsp" />
<script src="../js/bootstrap.bundle.min.js"></script>
</body>
</html>