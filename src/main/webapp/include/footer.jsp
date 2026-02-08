<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%--HTML 5 문서유형 선언--%>
<!DOCTYPE html>
<html>

<body>
    <%-- 푸터(웹사이트 최하단 영역) --%>
    <%-- 어두운 배경(dark background)에 흰색 텍스트로 푸터 설정 --%>
    <footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
	    © 눈송여자대학교 학사관리 시스템
	</footer>
    
    <%-- 필수 자바스크립트 라이브러리들 추가 --%>
    <script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
</body>

</html>