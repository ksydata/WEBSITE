<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>

<div class="d-flex justify-content-center mt-4">
    <nav>
    	<!-- 페이징 UI를 렌터링하는 공통 컴포넌트 -->
        <ul class="pagination">

            <!-- 이전 블록 이동 -->
            <c:if test="${startPage > 1}">
                <li class="page-item">
                    <a class="page-link"
                       href="${pageURL}?page=${startPage - 1}${paramStr}">&laquo;</a>
                </li>
            </c:if>

            <!-- 페이지 번호 출력 -->
            <c:forEach var="p" begin="${startPage}" end="${endPage}">
                <li class="page-item ${p == currentPage ? 'active' : ''}">
                    <a class="page-link"
                       href="${pageURL}?page=${p}${paramStr}">${p}</a>
                </li>
            </c:forEach>

            <!-- 다음 블록 이동 -->
            <c:if test="${endPage < totalPage}">
                <li class="page-item">
                    <a class="page-link"
                       href="${pageURL}?page=${endPage + 1}${paramStr}">&raquo;</a>
                </li>
            </c:if>

        </ul>
    </nav>
</div>

</body>
</html>