<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<!-- 
	학생 : 본인 userID로 성적 묶어서 제공, 페이징 없음
	교수 : 동일한 단과대에 소속된 학생들의 성적을 묶어서 제공, 페이징 있음
	관리자 : 모든 학생들의 성적을 제공, 페이징 있음
 -->
 
<head>
    <meta charset="UTF-8">
    <title>학사정보</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
</head>

<body>
<!-- 공통 상단 메뉴 -->
<jsp:include page="/include/header.jsp" />

<%-- pagingDTO에서 recordList 추출 (교수/관리자용); 학생은 기존 recordList 그대로 사용 --%>
<c:set var="recordList" value="${pagingDTO != null ? pagingDTO.pagingDataList : recordList}" />

<div class="container mt-5">
	<h2 class="text-center mb-4">성적 조회</h2>
	
	<!-- 학적 정보 카드 -->
	<div class="card mb-4">
	    <div class="card-header bg-primary text-white">
	        학적 정보
	    </div>
	    <div class="card-body">
	        <c:forEach var="entry" items="${profileViewMap}">
	            <p>
	                <strong>${entry.key}:</strong>
	                ${entry.value}
	            </p>
	        </c:forEach>
	    </div>
	</div>
    
    <!-- 성적 정보 카드 -->
    <div class="card">
        <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
            <span>성적 정보</span>
            <!-- 교수 권한일 때만 수정 버튼 노출 -->
            <c:if test="${sessionScope.role.isROLE_002()}">
                <a href="editAcademicRecord.jsp"
                   class="btn btn-light btn-sm"
                   onclick="return confirm('성적 수정 페이지로 이동하시겠습니까?');">
                    성적 수정
                </a>
            </c:if>
        </div>
        
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover text-center align-middle">
                    <thead class="thead-dark">
                        <tr>
                            <%-- 교수/관리자일 때 이름, 학번 컬럼 추가 --%>
                            <c:if test="${sessionScope.role.isROLE_002() || sessionScope.role.isROLE_004()}">
                                <th>이름</th>
                                <th>학번</th>
                            </c:if>
                            <th>수강연도</th>
                            <th>학기</th>
                            <th>과목명</th>
                            <th>이수구분</th>
                            <th>성적(등급)</th>
                            <th>성적(평점)</th>
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
                                <c:if test="${sessionScope.role.isROLE_002() || sessionScope.role.isROLE_004()}">
                                    <td>${record.name}</td>
                                    <td>${record.userID}</td>
                                </c:if>
                                <td>${record.academicYear}</td>
                                <td>${record.semester}</td>
                                <td>${record.courseName}</td>
                                <td>${record.courseType}</td>
                                <td>${record.grade}</td>
                                <td>${record.gradePoint}</td>
                                <td>${record.coursePF}</td>
                                <td>${record.passOrFail}</td>
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
            
            <%-- 교수/관리자 권한일 때 페이징 렌더링 --%>
            <c:if test="${pagingDTO != null && pagingDTO.totalPage > 1}">
                <nav class="mt-3">
                    <ul class="pagination justify-content-center">
                        <c:if test="${pagingDTO.startPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${pagingDTO.startPage - 1}">&laquo;</a>
                            </li>
                        </c:if>
                        <c:forEach begin="${pagingDTO.startPage}" end="${pagingDTO.endPage}" var="i">
                            <li class="page-item ${pagingDTO.currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${pagingDTO.endPage < pagingDTO.totalPage}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${pagingDTO.endPage + 1}">&raquo;</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>

</div>

<!-- 공통 푸터 -->
<jsp:include page="/include/footer.jsp" />

</body>
</html>