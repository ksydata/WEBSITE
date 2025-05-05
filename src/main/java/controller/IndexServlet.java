package controller;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 세션 검사, 권한 확인하는 프로세스는 자바 서블릿 도구로 분리해서 관리
/* 
1. userID뿐만 아니라 userRole, userName도 세션 체크
2. 정상적인 로그인이 되었을 경우, index.jsp 메인 페이지로 포워딩
3. 비정상적인 접근일 경우, login.jsp 로그인 페이지로 리다이렉트 
*/

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
	    // 3. 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
		if (session == null ||
				session.getAttribute("userID") == null ||
	            session.getAttribute("userName") == null ||
	            session.getAttribute("userRole") == null) {
			response.sendRedirect(request.getContextPath() + "/common/login.jsp");
            return;
		}
		// 1. 세션에서 로그인된 사용자 아이디, 성명, 계정 유형을 가져옴 
		// 사용자 정보를 jsp로 전달(Http 요청에 속성값 설정)
        request.setAttribute("userID", session.getAttribute("userID"));
        request.setAttribute("userName", session.getAttribute("userName"));
        request.setAttribute("userRole", session.getAttribute("userRole"));
		
		// 2. 로그인 성공하여 index.jsp 메인 페이지로 이동
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		// .getRequestDispatcher(getServletInfo()).forward(HttpServletRequest, HttpServletResponse)
	}
}


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