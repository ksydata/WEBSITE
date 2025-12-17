package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.UserInfoDTO;
import service.UserInfoService;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet  {

	private static final long serialVersionUID = 1L;	
	
	// 개인정보 조회 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		// [TO-BE] UserInfoServlet에서 UserInfoFilter로 이관
		// UTF-8로 인코딩 설정 (한글 깨짐 방지)
        // request.setCharacterEncoding("UTF-8");
        // response.setContentType("text/html;charset=UTF-8");
		
        // 로그인 시 세션에 저장된 학번/사번 불러오기
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
		
        // [TO-BE] UserInfoServlet에서 UserInfoFilter로 이관
        // if (userID == null) {
            // 세션이 없으면 로그인 페이지로 리다이렉트
            // response.sendRedirect(request.getContextPath() + "/login.jsp");
            // return;}
        
		// UserInfoDAO를 직접 부르는 게 아니라 UserInfoService 통해 데이터 전송
        UserInfoService userInfoService = new UserInfoService();
        UserInfoDTO userInfo = userInfoService.getUserInfo(userID);
		
		// 세션이 초기화되어 userID를 받아오지 못하고, professorInfo 객체를 가져오지 못하는 Null 오류 발생
		if (userInfo != null) {
		    request.setAttribute("userInfo", userInfo);
		    // JSP 페이지로 포워딩
			request.getRequestDispatcher("userInfo.jsp").forward(request, response);
			
		} else {
			// 아이디로 받아 세션에 저장된 사번으로 사용자 1명의 정보를 가져오지 못한 경우 메인으로 이동
			response.sendRedirect(request.getContextPath() + "userInfo/main.jsp");
		}
	}
	
	// 개인정보 수정 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 세션값에 저장된 사번 불러오기
        String userID = (String) request.getSession().getAttribute("userID");

        // [TO-BE] UserInfoServlet에서 UserInfoFilter로 이관
        // 사번이 없으면 로그인 페이지로 리다이렉트
		
        // 수정대상 개인정보(휴대전화번호, 사무실전화번호, 이메일, 주소)
        String phoneNumber = request.getParameter("phoneNumber");
        String officeNumber = request.getParameter("officeNumber");        
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        
        // 비즈니스 로직을 정의한 Service 계층 호출하여 DB 테이블에 사용자에 의해 수정된 개인정보 업데이트
        UserInfoService userInfoService = new UserInfoService();
        userInfoService.updateUserInfo(userID, phoneNumber, officeNumber, email, address);
        
        // 개인정보 수정 완료 후 알림
        request.setAttribute("message", "개인정보가 성공적으로 수정되었습니다.");
        // 수정된 정보로 HTTP 웹에 다시 GET 메서드 수행 요청(데이터 조회)
        doGet(request, response);
	}
}
