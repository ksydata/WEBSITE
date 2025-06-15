package controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.AdminPersonalInfoDTO;
import service.AdminService;

@WebServlet("/adminInfo")
public class AdminInfoServlet extends HttpServlet  {

	private static final long serialVersionUID = 1L;	
	
	// 개인정보 조회 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 로그인 시 세션에 저장된 사번 불러오기
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("userID");
		
        if (userID == null) {
            // 세션이 없으면 로그인 페이지로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
		// ProfessorDAO를 직접 부르는 게 아니라 ProfessorService 통해 데이터 전송
        AdminService adminService = new AdminService();
        AdminPersonalInfoDTO adminInfo = adminService.getAdminInfo(userID);
		
		// 세션이 초기화되어 userID를 받아오지 못하고, professorInfo 객체를 가져오지 못하는 Null 오류 발생
		if (adminInfo != null) {
		    request.setAttribute("adminInfo", adminInfo);
		    // JSP 페이지로 포워딩
			request.getRequestDispatcher("admin/myPersonalInfo.jsp").forward(request, response);
			
		} else {
			// 아이디로 받아 세션에 저장된 사번으로 교수 1명의 정보를 가져오지 못한 경우 메인으로 이동
			response.sendRedirect(request.getContextPath() + "admin/main.jsp");
		}
	}
}
