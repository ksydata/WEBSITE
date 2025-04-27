package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GET 방식 요청 처리 (로그아웃 기능)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 세션 해제하여 로그인 정보 끊기
    	request.getSession().invalidate();
    	// 로그인 페이지로 리다이렉트 (즉, 홈페이지로 다시 이동)
        response.sendRedirect(request.getContextPath() + "/index.jsp"); // 메인페이지로 이동
    }
}
