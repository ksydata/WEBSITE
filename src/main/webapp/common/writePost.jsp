<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.NoticeDAO, dto.NoticeDTO" %>
<%
    String userID = (String) session.getAttribute("userID");
	String permissionRole = (String) session.getAttribute("permissionRole");

	if (userID == null || permissionRole == null) {
	    response.sendRedirect("login.jsp");
	    return;
	}
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

    <form action="BoardServlet" method="post">
    	<!-- 숨겨진 필드로 userID, permissionRole 전달 -->
    	<input type="hidden" name="userID" value="<%= userID %>">
    	<input type="hidden" name="permissionRole" value="<%= permissionRole %>">
    	
    
        <!-- <div class="mb-3">
            <label for="userID" class="form-label">작성자 ID</label>
            <input type="text" class="form-control" id="userID" name="userID" required>
        </div> -->

        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>

        <div class="mb-3">
            <label for="contents" class="form-label">내용</label>
            <textarea class="form-control" id="contents" name="contents" rows="6" required></textarea>
        </div>

       <!-- <div class="mb-3">
            <label for="permissionRole" class="form-label">권한</label>
            <select class="form-select" id="permissionRole" name="permissionRole">
                <option value="student">학생</option>
                <option value="professor">교수</option>
                <option value="admin">관리자</option>
            </select>
        </div>  -->>

        <button type="submit" class="btn btn-primary">등록</button>
        <a href="postlist.jsp" class="btn btn-secondary">취소</a>
    </form>
</body>
</html>