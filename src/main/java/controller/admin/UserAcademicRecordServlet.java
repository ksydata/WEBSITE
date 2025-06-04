package controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminRecordDTO;
import service.AdminService;

@WebServlet("/userRecord")
public class UserAcademicRecordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 요청 파라미터를 UTF-8로 인코딩 (한글 깨짐 방지)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String userID = request.getParameter("id");
        
        AdminService service = new AdminService();
        List<AdminRecordDTO> recordList = service.getRecordsByStudent(userID);
        request.setAttribute("recordList", recordList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userAcademicRecord.jsp");
        dispatcher.forward(request, response); 
	}
}
