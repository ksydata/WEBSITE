<%@ page language="java" contentType="text/html; charset=UTF-8" %>
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
<jsp:include page="../include/header.jsp" />

<!-- 공통 검색어 입력창 -->
<%-- <jsp:include page="../include/searchForm.jsp" />
 --%><!-- 필요한 조건 검색 필터 (예: 컬럼 선택) -->
<form action="/classRecord" method="get" class="row g-2 mb-3">
	<!-- 선택 필터 컬럼 정의 -->
    <!-- <div class="col-md-3">
        <label for="searchColumn" class="form-label">검색 기준</label>
        <select name="searchColumn" id="searchColumn" class="form-select">
            <option value="userID">학번</option>
            <option value="name">이름</option>
            <option value="college">전공</option>
            <option value="courseName">과목</option>
            <option value="academicYear">수강연도</option>
            <option value="semester">학기</option>
        </select>
    </div> -->
</form>

<!-- 교수의 소속 단과대학 전공생 학점 테이블 -->
<h2>전공생 학사정보</h2>
<div class="table-responsive px-3">
<table class="table table-bordered table-hover text-center">
    <thead class="table-primary">
    <tr>
        <!-- 기존 드롭다운 필터 방식 대신 컬럼 헤더 자체를 클릭하여 정렬하는 방식으로 변경 -->
    	<c:set var="currentSort" value="${param.sortColumn}" />
    	<c:set var="currentOrder" value="${param.sortOrder}" />
    	<c:set var="nextOrder" value="${currentOrder == 'asc' ? 'desc' : 'asc'}" />
    	
    	<!-- 오름차순 또는 내림차순 정렬 토글 및 아이콘 표시 : 학번, 등급, 평점, 수강연도, 학기 -->
	    <%-- <th>
	        <a href="?sortColumn=userID&sortOrder=${currentSort eq 'userID' ? nextOrder : 'asc'}">학번
	            <c:if test="${currentSort eq 'userID'}">
	                <span class="ms-1">${currentOrder eq 'asc' ? "▲" : "▼"}</span>
	            </c:if>
	        </a>
	    </th> --%>
	    <th>학번</th>
        <th>이름</th>
        <th>단과대학</th>        
        <th>전공</th>
        <th>과목</th>
        <th>등급</th>
        <th>평점</th>
        <th>수강연도</th>
        <th>학기</th>
        
    	<!-- 오름차순 또는 내림차순 정렬 토글 및 아이콘 표시 : 학번, 등급, 평점, 수강연도, 학기 -->        
	    <%-- <th>
	        <a href="?sortColumn=grade&sortOrder=${currentSort eq 'grade' ? nextOrder : 'asc'}">등급
	            <c:if test="${currentSort eq 'grade'}">
	                <span class="ms-1">${currentOrder eq 'asc' ? "▲" : "▼"}</span>
	            </c:if>
	        </a>
	    </th> --%>
	    
	    <%-- <th>
	        <a href="?sortColumn=gradePoint&sortOrder=${currentSort eq 'gradePoint' ? nextOrder : 'asc'}">평점
	            <c:if test="${currentSort eq 'gradePoint'}">
	                <span class="ms-1">${currentOrder eq 'asc' ? "▲" : "▼"}</span>
	            </c:if>
	        </a>
	    </th> --%>
	    
	    <%-- <th>
	        <a href="?sortColumn=academicYear&sortOrder=${currentSort eq 'academicYear' ? nextOrder : 'asc'}">수강연도
	            <c:if test="${currentSort eq 'academicYear'}">
	                <span class="ms-1">${currentOrder eq 'asc' ? "▲" : "▼"}</span>
	            </c:if>
	        </a>
	    </th> --%>
	    
	
	    <%-- <th>
	        <a href="?sortColumn=semester&sortOrder=${currentSort eq 'semester' ? nextOrder : 'asc'}">학기
	            <c:if test="${currentSort eq 'semester'}">
	                <span class="ms-1">${currentOrder eq 'asc' ? "▲" : "▼"}</span>
	            </c:if>
	        </a>
	    </th> --%>
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
<!-- 페이징 출력 -->
<%@ include file="/include/paging.jsp" %>

<!-- 하단 영역의 메인 페이지로 돌아가는 링크 -->
<br>
<a href="<c:url value='common/main.jsp' />" class="btn btn-secondary">메인으로 돌아가기</a>
<!-- 공통 푸터 -->
<jsp:include page="../include/footer.jsp" />
<script src="../js/bootstrap.bundle.min.js"></script>
</body>
</html>

<!-- https://getbootstrap.kr/docs/5.0/layout/grid/ -->
<!-- 
	// 정렬 필터 컬럼 정의
    <div class="col-md-3">
        <label for="sortColumn" class="form-label">정렬 기준</label>
        <select name="sortColumn" id="sortColumn" class="form-select">
            <option value="userID">학번</option>
            <option value="grade">등급</option>
            <option value="gradePoint">평점</option>			
            <option value="academicYear">수강연도</option>
            <option value="semester">학기</option>		
        </select>
    </div>

	// 정렬 기준 설정
    <div class="col-md-3">
        <label for="sortOrder" class="form-label">정렬 순서</label>
        <select name="sortOrder" id="sortOrder" class="form-select">
            <option value="asc">오름차순</option>
            <option value="desc">내림차순</option>			
        </select>
    </div>
    
    // 검색조건 또는 정렬 필터 적용 버튼 생성
	<div class="col-md-3 d-flex align-items-end">
		<button type="submit" class="btn btn-outline-primary w-100">필터 적용</button>
	</div>
 -->