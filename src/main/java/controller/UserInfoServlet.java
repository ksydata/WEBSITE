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
import util.RoleEnum;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet  {

	private static final long serialVersionUID = 1L;	
	
	// 개인정보 조회 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		// [TO-BE] UserInfoServlet에서 UserInfoFilter로 이관
		// UTF-8로 인코딩 설정 (한글 깨짐 방지)
		
        // 로그인 시 세션에 저장된 학번/사번 불러오기
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
        RoleEnum role = (RoleEnum) session.getAttribute("role");
		
        // [TO-BE] UserInfoServlet에서 UserInfoFilter로 이관
        // 세션이 없으면 로그인 페이지로 리다이렉트
        
		// UserInfoDAO를 직접 부르는 게 아니라 UserInfoService 통해 데이터 전송
        UserInfoService userInfoService = new UserInfoService();
		
        // UserInfo 쪽에서는 AcademicRecord과 달리 ProfileViewMap 필요하지 않음
        // 역할별로 보여줄 항목이 다르기 때문
		try {
			if (role.isROLE_001() || role.isROLE_002()) {
				UserInfoDTO myInfo = userInfoService.getUserInfo(userID, role, 1);
				// Type mismatch: cannot convert from void to UserInfoDTO
			    request.setAttribute("userInfo", myInfo);
			    // JSP 페이지로 포워딩

			} else if (role.isROLE_003() || role.isROLE_004()) {
				// [AS-IS] PagingDTO, PagingService
				// request.setAttribute("pagingDTO", pagingDTO);
			}
			
			request.getRequestDispatcher("/common/info/myPersonalInfo.jsp")
				.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// [AS-IS] 아이디로 받아 세션에 저장된 사번으로 사용자 1명의 정보를 가져오지 못한 경우 메인으로 이동
			response.sendRedirect(request.getContextPath() + "/main.jsp");
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
        // response.sendRedirect(request.getContextPath() + "/userInfo");
	}
}
