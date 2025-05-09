<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/header.jsp" %>
<%@ page import="java.util.*, dao.NoticeDAO, dto.NoticeDTO" %>
<%
    NoticeDAO dao = new NoticeDAO();
    List<NoticeDTO> noticeList = dao.getAllNotices();
    request.setAttribute("noticeList", noticeList);
%>
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

<body class="container mt-5">
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<h2 class="mb-4">📋 게시판 글 목록</h2>

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
            <c:forEach var="post" items="${noticeList}">
                <tr onclick="location.href='postpage.jsp?id=${post.noticeID}'" style="cursor:pointer;">
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

    <div class="text-end">
        <a href="writePost.jsp" class="btn btn-primary">글쓰기</a>
    </div>
	
	</nav>
</body>
</html>

<%@ include file="/common/footer.jsp" %>