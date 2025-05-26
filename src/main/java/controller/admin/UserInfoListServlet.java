package controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dto.AdminPersonalInfoDTO;

@WebServlet("/adminUserList")
public class UserInfoListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 개인정보 리스트 조회 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 인코딩
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 파라미터에서 역할(role) 정보 가져오기
        String userRole = request.getParameter("role");  // 예: "학생", "교수", "전체" 등
        
        AdminDAO dao = new AdminDAO();
        List<AdminPersonalInfoDTO> list = dao.getUserListByRole(userRole);  // 역할 인자 전달
	
        request.setAttribute("userList", list);
        request.setAttribute("selectedRole", userRole);  // 선택된 역할을 JSP에서 표시
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userInfoList.jsp");
        dispatcher.forward(request, response); 
	}
	
}
