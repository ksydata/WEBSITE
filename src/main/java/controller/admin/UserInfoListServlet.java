package controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.AdminPersonalInfoDTO;
import service.AdminService;

@WebServlet("/adminUserList")
public class UserInfoListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 개인정보 리스트 조회 메서드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");

	    String userRole = request.getParameter("role");
	    String pageParam = request.getParameter("page");
	    int page = 1;
	    
	    
	    if (pageParam != null && pageParam.matches("\\d+")) {
	        page = Integer.parseInt(pageParam);
	    }

	    AdminService service = new AdminService();
	    List<AdminPersonalInfoDTO> list = service.getPagedUserList(userRole, page);
	    int totalPage = service.getTotalPageCount(userRole);
	    
	    int blockSize = 10; // 10개 단위 블록
	    int startPage = ((page - 1) / blockSize) * blockSize + 1;
	    int endPage = Math.min(startPage + blockSize - 1, totalPage);

	    request.setAttribute("userList", list);
	    request.setAttribute("selectedRole", userRole);
	    request.setAttribute("currentPage", page);
	    request.setAttribute("startPage", startPage);
	    request.setAttribute("endPage", endPage);
	    request.setAttribute("totalPage", totalPage);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userInfoList.jsp");
	    dispatcher.forward(request, response);
	}
	
}
