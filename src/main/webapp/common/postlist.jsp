<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*, dao.NoticeDAO, dto.NoticeDTO" %>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시글 목록</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- 부트스트랩 스타일 시트 연결 href="./css/bootstrap.min.css?ver=1" -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<!-- 사용자 정의(커스텀) 스타일 시트 연결 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>

<body> 
<div class="container mt-5">
	<h2 class="text-center mb-4">📋 게시판 글 목록</h2>
	
	<!--  삭제 완료 팝업 띄우기 -->
	<c:if test="${not empty sessionScope.flashMessage}">
	    <script>
	        alert("${sessionScope.flashMessage}");
	    </script>
	    <c:remove var="flashMessage" scope="session" />
	</c:if>
	
	<!-- 게시판 글목록 우측 상단의 글쓰기 버튼 생성 -->
	<div class="text-right">
        <!-- a태그 내 같은 링크를 클릭 시 브라우저 페이지가 위로 튀는 걸 막기 위해 
        이벤트 관련 javascript 메서드를 onclick을 통해 글쓰기 버튼에 기능 포함 -->		
        <a href="${pageContext.request.contextPath}/post" class="btn btn-primary"
   		onclick="event.preventDefault(); document.getElementById('writeForm').submit();">글쓰기</a>
        <!-- <a href="common/writePost.jsp" class="btn btn-primary">글쓰기</a> -->
   		<!-- 글쓰기 버튼을 통해 PostServlet의 doPost()를 활용하여 writePost.jsp 화면으로 이동 -->
		<form id="writeForm" action="${pageContext.request.contextPath}/post" method="post" style="display: none;"></form>
    </div>
    
    <!-- 게시판 글목록 전체 조회 -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <table class="table table-hover">
        <thead class="table-dark">
            <tr>
                <th>index</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>제목</th>
            </tr>
        </thead>
        <tbody>
   		<!-- BoardServlet의 doGet()을 활용하여 noticeList 객체로 공지글에 필요한 데이터 불러오기 -->        
            <c:forEach var="post" items="${noticeList}">
               <tr onclick="location.href='post?id=${post.noticeID}'" style="cursor:pointer;">
                    <td>${post.noticeID}</td>
                    <td>${post.userID}</td>
					<td>
					    <fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="Asia/Seoul" />
					</td>                    
					<td>${post.title}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	</nav>
	
<!-- 페이징 블록 -->
<div class="text-center mt-4">
    <nav>
   	<!-- BoardServlet의 doGet()을 활용하여 이동 currentPage(현재 페이지 번호)와 totalPages(마지막 페이지 수) 객체를 게시판 공지글 리스트 하단에 불러오기 -->     
        <ul class="pagination justify-content-center">
        	<!-- 서비스단으로 이관이 필요한 로직 (페이지 이전/다음으로 가는 조건 분기) -->
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="board?page=${currentPage - 1}">이전</a>
                </li>
            </c:if>

<!-- 페이징 출력 -->
<%@ include file="/common/paging.jsp" %>
</div>
</body>
</html>

<%@ include file="/common/footer.jsp" %>