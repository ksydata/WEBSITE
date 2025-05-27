<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>학생 목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/custom.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">🎓 학생 목록</h2>

    <!-- Flash 메시지 출력 -->
    <c:if test="${not empty sessionScope.flashMessage}">
        <script>alert("${sessionScope.flashMessage}");</script>
        <c:remove var="flashMessage" scope="session" />
    </c:if>

    <table class="table table-hover">
        <thead class="table-dark">
            <tr>
                <th>학생 ID</th>
                <th>이름</th>
                <th>이메일</th>
                <th>단과대학</th>
                <th>전공</th>
                <th>입학년도</th>
                <th>재학상태</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${studentList}">
                <tr onclick="location.href='userRecord?id=${student.userID}'" style="cursor: pointer;">
                    <td>${student.userID}</td>
                    <td>${student.name}</td>
                    <td>${student.email}</td>
                    <td>${student.college}</td>
                    <td>${student.major}</td>
                    <td>${student.admissionYear}</td>
                    <td>${student.status}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
<%@ include file="/common/footer.jsp" %>