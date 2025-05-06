<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dao.NoticeDAO, dto.NoticeDTO" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    NoticeDAO dao = new NoticeDAO();
    NoticeDTO post = dao.getNoticeByID(id);

    String sessionUserID = (String) session.getAttribute("userID");

    if (sessionUserID == null || !sessionUserID.equals(post.getUserID())) {
    	response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("<script>alert('올바른 계정으로 접근하십시오.'); history.back();</script>");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시글 수정</title>
	<link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/custom.css">
</head>
<body class="container mt-5">
	<h2 class="mb-4">📝 게시글 수정</h2>
	<form action="<%= request.getContextPath() %>/EditServlet" method="post">
	    <input type="hidden" name="id" value="<%= post.getNoticeID() %>">
	
	    <div class="form-group">
	        <label>제목</label>
	        <input type="text" name="title" class="form-control" value="<%= post.getTitle() %>" required>
	    </div>
	
	    <div class="form-group">
	        <label>내용</label>
	        <textarea name="contents" class="form-control" rows="6" required><%= post.getContents() %></textarea>
	    </div>
	
	    <button type="submit" class="btn btn-primary">수정 완료</button>
	</form>

</body>
</html>