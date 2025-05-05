<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.NoticeDAO, dto.NoticeDTO" %>
<%
    String userID = (String) session.getAttribute("userID");
	String permissionRole = (String) session.getAttribute("permissionRole");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/custom.css">
</head>
<body class="container mt-5">
    <h2 class="mb-4">📝 게시글 작성</h2>

    <form action="<%= request.getContextPath() %>/BoardServlet" method="post">
    	<!-- 숨겨진 필드로 userID, permissionRole 전달 -->
    	<input type="hidden" name="userID" value="<%= userID %>">
    	<input type="hidden" name="permissionRole" value="<%= permissionRole %>">

        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>

        <div class="mb-3">
            <label for="contents" class="form-label">내용</label>
            <textarea class="form-control" id="contents" name="contents" rows="6" required></textarea>
        </div>
        
        <div class="mb-3">
    		<label for="endDate" class="form-label">공지 종료일시</label>
    		<input type="datetime-local" class="form-control" id="endDate" name="endDate">
		</div>

        <button type="submit" class="btn btn-primary">등록</button>
        <a href="postlist.jsp" class="btn btn-secondary">취소</a>
    </form>
</body>
</html>