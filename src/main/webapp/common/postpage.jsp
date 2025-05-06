<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dao.NoticeDAO, dto.NoticeDTO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    NoticeDAO dao = new NoticeDAO();
    NoticeDTO post = dao.getNoticeByID(id);
    request.setAttribute("post", post);
    
    String loginUserID = (String) session.getAttribute("userID");
    String loginUserRole = (String) session.getAttribute("role"); // 예: 관리자, 교직원, 학생, 교수
    boolean isAuthor = loginUserID != null && loginUserID.equals(post.getUserID());
    boolean isAdmin = loginUserRole != null && loginUserRole.equals("관리자");
    boolean canDelete = isAuthor || isAdmin;
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${post.title}</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- 부트스트랩 스타일 시트 연결 href="./css/bootstrap.min.css?ver=1" -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<!-- 사용자 정의(커스텀) 스타일 시트 연결 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>
<body class="container mt-5">
	<h2 class="mb-3">${post.title}</h2>
    <p><strong>작성자:</strong> ${post.userID}</p>
    
    <p><strong>작성일:</strong> <fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="Asia/Seoul" /></p>
    <p><strong>수정일:</strong> <fmt:formatDate value="${post.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="Asia/Seoul" /></p>
    <p><strong>종료일:</strong> <fmt:formatDate value="${post.endDate}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="Asia/Seoul" /></p>

    <hr>
    	<p>${post.contents}</p>
    <hr>

    <div class="d-flex justify-content-between">
	    <a href="postlist.jsp" class="btn btn-secondary">목록으로</a>
	
	    <div class="d-flex gap-2">
	        <%-- 수정 버튼: 작성자만 가능 --%>
	        <% if (isAuthor) { %>
	            <a href="editPost.jsp?id=<%= post.getNoticeID() %>" class="btn btn-warning">수정</a>
	        <% } %>
	
	        <%-- 삭제 버튼: 작성자 또는 관리자 --%>
	        <% if (canDelete) { %>
	            <form action="<%= request.getContextPath() %>/DeleteServlet" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');" style="margin: 0;">
	                <input type="hidden" name="id" value="<%= post.getNoticeID() %>">
	                <button type="submit" class="btn btn-warning">삭제</button>
	            </form>
	        <% } %>
    </div>
</div>
</body>
</html>