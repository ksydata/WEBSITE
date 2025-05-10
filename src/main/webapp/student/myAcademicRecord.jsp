<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*, dao.RecordDAO, dto.RecordDTO" %>
<%
    String userID = (String) session.getAttribute("userID");
    if (userID != null) {
        RecordDAO dao = new RecordDAO();
        List<RecordDTO> recordList = dao.getRecordsByStudent(userID);
        request.setAttribute("recordList", recordList);
    } else {
        response.sendRedirect("login.jsp");
    }
%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>나의 학사정보 조회</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>

<body>
<jsp:include page="../common/header.jsp" />

<div class="container mt-5">
    <h2 class="text-center mb-4">나의 학사정보 조회</h2>

    <!-- 학생 정보 섹션 -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            학생 정보
        </div>
        <div class="card-body">
            <p><strong>이름:</strong> ${userName}</p>
        </div>
    </div>

    <!-- 성적 테이블 섹션 -->
    <div class="card">
        <div class="card-header bg-secondary text-white">
            성적 정보
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover text-center align-middle">
                    <thead class="thead-dark">
                        <tr>
                            <th>수강연도</th>
                            <th>학기</th>
                            <th>구분</th>
                            <th>단과대학</th>
                            <th>전공</th>
                            <th>과목명</th>
                            <th>평점(알파벳)</th>
                            <th>평점(숫자)</th>
                            <th>P/F 여부</th>
                            <th>P/F</th>
                            <th>재수강 년도</th>
                            <th>재수강 학기</th>
                            <th>재수강 사유</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="record" items="${recordList}">
                            <tr>
                                <td>${record.academicYear}</td>
                                <td>${record.semester}</td>
                                <td>${record.courseType}</td>
                                <td>${record.college}</td>
                                <td>${record.major}</td>
                                <td>${record.courseName}</td>
                                <td>${record.grade}</td>
                                <td>${record.gradePoint}</td>
                                <td>${record.passOrFail}</td>
                                <td>${record.coursePF}</td>
                                <td>${record.retakeYear}</td>
                                <td>${record.retakeSemester}</td>
                                <td>${record.enrollmentReason}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty recordList}">
                            <tr>
                                <td colspan="13">성적 정보가 없습니다.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
<script src="../js/jquery.min.js"></script>
<script src="../js/popper.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
</body>

</html>