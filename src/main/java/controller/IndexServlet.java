// 세션 검사, 권한 확인하는 프로세스는 자바 서블릿 도구로 분리해서 관리
/* 
1. userID뿐만 아니라 userRole, userName도 세션 체크
2. 정상적인 로그인이 되었을 경우, index.jsp 메인 페이지로 포워딩
3. 비정상적인 접근일 경우, login.jsp 로그인 페이지로 리다이렉트 
*/




/*
[index.jsp]
<%
	// 세션에서 로그인된 사용자 ID를 가져옴
	String userID = (String) session.getAttribute("userID");

	// 로그인되어있지 않지만 로그인 페이지로 리다이렉트
	if (userID == null) {
		// 로그인 페이지로 이동
		response.sendRedirect("common/login.jsp");
		// 이후 JSP 실행 중단
		return;
	}
%>
*/