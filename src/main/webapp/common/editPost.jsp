<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
</head>
<body class="container mt-5">
    <h2 class="mb-4">📝 게시글 수정</h2>
    <form action="${pageContext.request.contextPath}/EditServlet" method="post">
        <input type="hidden" name="id" value="${post.noticeID}" />
        <div class="form-group">
            <label>제목</label>
            <input type="text" name="title" class="form-control" value="${post.title}" required />
        </div>

        <div class="form-group">
            <label>내용</label>
            <textarea name="contents" class="form-control" rows="6" required>${post.contents}</textarea>
        </div>

        <button type="submit" class="btn btn-primary">수정 완료</button>
    </form>
</body>
</html>