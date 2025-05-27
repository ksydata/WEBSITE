package controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdminDAO;
import dto.AdminPersonalInfoDTO;

@WebServlet("/userInfo")
public class UserInfoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
		
		String id = request.getParameter("id");
		
		AdminDAO dao = new AdminDAO();
		AdminPersonalInfoDTO info = dao.getUserInfo(id);
		request.setAttribute("studentInfo", info);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userPersonalInfo.jsp");
		dispatcher.forward(request, response);
	}

}
