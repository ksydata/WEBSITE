<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dao.NoticeDAO, dto.NoticeDTO" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    NoticeDAO dao = new NoticeDAO();
    NoticeDTO post = dao.getNoticeByID(id);
    request.setAttribute("post", post);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- 부트스트랩 스타일 시트 연결 href="./css/bootstrap.min.css?ver=1" -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<!-- 사용자 정의(커스텀) 스타일 시트 연결 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>
<body class="container mt-5">
	<h2 class="mb-3">${post.title}</h2>
    <p><strong>작성자:</strong> ${post.userID}</p>
    <p><strong>작성일:</strong> ${post.createDate}</p>
    <p><strong>수정일:</strong> ${post.updateDate}</p>
    <p><strong>종료일:</strong> ${post.endDate}</p>

    <hr>
    <p>${post.contents}</p>
    <hr>

    <div class="d-flex justify-content-between">
        <a href="postlist.jsp" class="btn btn-secondary">목록으로</a>
        <div>
            <a href="editPost.jsp?id=${post.noticeID}" class="btn btn-warning">수정</a>
            <a href="deletePost.jsp?id=${post.noticeID}" class="btn btn-danger">삭제</a>
        </div>
    </div>
</body>
</html>