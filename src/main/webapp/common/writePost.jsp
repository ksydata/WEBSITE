<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    /* 
     * JSP 직접 접근 방지 로직 추가
     * 사용자가 URL에 직접 /common/writePost.jsp 를 입력했을 때,
     * 세션을 거치지 않으므로 requestScope에 userID가 존재하지 않음.
     * 따라서 /write 서블릿으로 리다이렉트하여 doGet()을 통해 정상 진입시키기 위함.
     */
    if (request.getAttribute("userID") == null) {
        response.sendRedirect(request.getContextPath() + "/write");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <!-- (수정) 절대경로로 변경하여 CSS가 어느 위치에서든 로드되도록 처리 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
</head>
<body class="container mt-5">
    <h2 class="mb-4">게시글 작성</h2>

    <!-- (유지) form action은 WriteServlet의 @WebServlet("/write")과 매핑됨 -->
    <form action="${pageContext.request.contextPath}/write" method="post">
        <!-- form action="${pageContext.request.contextPath}/board" method="post" -->
        <!-- 숨겨진 필드로 userID, permissionRole 전달 -->
        <input type="hidden" name="userID" value="${userID}">
        <input type="hidden" name="permissionRole" value="${permissionRole}">

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

        <!-- (추가) 사용자가 작성 중 페이지를 잘못 종료하는 경우를 대비해 안내 주석 추가 -->
        <!-- endDate는 선택 입력 항목으로, null일 경우 DB에서는 공지 상시 게시로 처리 -->

        <button type="submit" class="btn btn-primary">등록</button>
        <!-- (수정) 목록으로 돌아갈 때 절대경로로 수정 -->
        <a href="${pageContext.request.contextPath}/board" class="btn btn-secondary">취소</a>
    </form>
</body>
</html>