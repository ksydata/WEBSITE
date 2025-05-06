package controller;

import user.UserDAO;
import user.UserDTO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


// WebServlet 어노테이션으로 url 매핑
// @WebServlet("/common/loginAction")
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
		request.setCharacterEncoding("UTF-8");

        // 사용자로부터 전달받은 아이디, 비밀번호 및 계정 유형 파라미터 가져오기
	    String userID = request.getParameter("userID");
	    String userPassword = request.getParameter("userPassword");
	    String userRole = request.getParameter("userRole");
	    String userName = request.getParameter("userName");	    	    

	    // 데이터접근객체 클래스의 인스턴스 생성 후 로그인 메서드를 활용하여 결과값 받아오기
	    UserDAO userDAO = new UserDAO();
	    UserDTO result = userDAO.login(userID, userPassword);

	    // 로그인 성공
	    if (result != null) { // result == 1
	    	HttpSession session = request.getSession();
	    	// 계정 아이디를 세션에 저장
	    	session.setAttribute("userID", userID);
	    	// 계정별 권한(학생, 교수, 교직원, 관리자)을 세션에 저장
	    	session.setAttribute("role", userRole);
	    	// 사용자 이름을 세션에 저장
	    	session.setAttribute("name", userName);
	    	
	        response.sendRedirect("index.jsp");
	    } else {
	        // 응답의 콘텐츠 타입을 UTF-8로 설정
	    	response.setContentType("text/html;charset=UTF-8");
	    	
	    	// 로그인 실패
	    	response.getWriter().println("<script>");	    	
            response.getWriter().println("alert('아이디 또는 비밀번호가 일치하지 않습니다.');");
            response.getWriter().println("history.back();");
            response.getWriter().println("</script>");
	    }
	}
}

/*
[Login Error 초기 소스코드]

if (result == 0) {
	response.getWriter().println("alert('비밀번호가 일치하지 않습니다.')");
} else if (result == -1) {
	response.getWriter().println("alert('존재하지 않는 아이디입니다.');");
} else if (result == -2) {
	response.getWriter().println("alert('데이터베이스 관련 오류가 발생했습니다.');");
} else {
	response.getWriter().println("alert('로그인 중 알 수 없는 오류가 발생했습니다.');");
}
response.getWriter().println("history.back();");
response.getWriter().println("</script>");
*/