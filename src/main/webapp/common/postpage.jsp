<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-5">
	<h2 class="mb-3">${post.title}</h2>
    <p><strong>작성자:</strong> ${post.userID}</p>
    
    <p><strong>작성일:</strong> <fmt:formatDate value="${post.createDate}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="Asia/Seoul" /></p>
    <p><strong>수정일:</strong> <fmt:formatDate value="${post.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="Asia/Seoul" /></p>
    <p><strong>종료일:</strong> <fmt:formatDate value="${post.endDate}" pattern="yyyy-MM-dd HH:mm:ss" timeZone="Asia/Seoul" /></p>

    <hr>
    	<p>${post.contents}</p>
    <hr>

    <div class="d-flex justify-content-between">
	    <a href="<c:url value='/board' />" class="btn btn-secondary">목록으로</a>
	
	    <div class="d-flex gap-2">
	        <%-- 수정 버튼: 작성자만 가능 --%>
	        <c:if test="${isAuthor}">
				<a href="${pageContext.request.contextPath}/EditServlet?id=${post.noticeID}" class="btn btn-warning">수정</a>
			</c:if>
	
	        <%-- 삭제 버튼: 작성자 또는 관리자 --%>
			<c:if test="${canDelete}">
			    <form action="<c:url value='/DeleteServlet' />" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');" style="margin: 0;">
			        <input type="hidden" name="id" value="${post.noticeID}">
			        <button type="submit" class="btn btn-warning">삭제</button>
    			</form>
			</c:if>
   		 </div>
	</div>
</div>
<jsp:include page="../common/footer.jsp" />
</body>
</html>