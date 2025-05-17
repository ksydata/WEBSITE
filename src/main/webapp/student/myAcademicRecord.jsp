<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>나의 학사정보</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>

<body>
<!-- 공통 상단 메뉴 -->
<jsp:include page="../common/header.jsp" />

<div class="container mt-5">
    <h2 class="text-center mb-4">나의 성적 조회</h2>

    <!-- 사용자 이름, 단과대학, 전공 등 학적 정보 카드 -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            학적 정보
        </div>
        <div class="card-body">
            <p><strong>이름:</strong> ${userName}</p>
            <p><strong>단과대학:</strong> ${recordList[0].college}></p>
            <p><strong>전공:</strong> ${recordList[0].major}</p>
        </div>
    </div>

    <!-- 학기별 성적 정보 카드 -->
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
                            <th>과목명</th>
                            <th>이수구분</th>
                            <th>성적(등급)</th>
                            <th>성적(평점)</th>
                            <th>P/F 여부</th>
                            <th>재수강 연도</th>
                            <th>재수강 학기</th>
                            <th>재수강 사유</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- 성적 리스트 반복 출력 -->
                        <c:forEach var="record" items="${recordList}">
                            <tr>
                                <td>${record.academicYear}</td>
                                <td>${record.semester}</td>
                                <td>${record.courseName}</td>
                                <td>${record.courseType}</td>
                                <td>${record.grade}</td>
                                <td>${record.gradePoint}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${record.passOrFail}">이수(Pass)</c:when>
                                        <c:otherwise>미이수(Fail)</c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:out value="${record.retakeYear}" default="-" />
                                </td>
                                <td>
                                    <c:out value="${record.retakeSemester}" default="-" />
                                </td>
                                <td>
                                    <c:out value="${record.enrollmentReason}" default="-" />
                                </td>
                            </tr>
                        </c:forEach>

                        <!-- 성적이 없을 경우 메시지 출력 -->
                        <c:if test="${empty recordList}">
                            <tr>
                                <td colspan="10">성적 정보가 없습니다.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- 공통 푸터 -->
<jsp:include page="../common/footer.jsp" />
</body>

</html>