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

@WebServlet("/studentRecordList")
public class UserRecordListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 학생 리스트 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 인코딩
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
		
		AdminDAO dao = new AdminDAO();
		List<AdminPersonalInfoDTO> list = dao.getUserListByRole("학생");
	
		request.setAttribute("studentList", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userRecordList.jsp");
		dispatcher.forward(request, response);  
	}
}
