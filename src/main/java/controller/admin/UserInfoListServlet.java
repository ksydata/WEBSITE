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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");

	    String userRole = request.getParameter("role"); // 권한 필터
	    String pageParam = request.getParameter("page");
	    String keyword = request.getParameter("searchKeyword"); // 검색어
	    String filterType = request.getParameter("searchType"); // 검색 필터: userID, name, email, major 등
	    
	    int page = 1;
	    if (pageParam != null && pageParam.matches("\\d+")) {
	        page = Integer.parseInt(pageParam);
	    }

	    AdminService service = new AdminService();
	    List<AdminPersonalInfoDTO> userList;
	    int totalPage;

	    if (keyword != null && !keyword.trim().isEmpty()) {
	        // 검색 조건이 있는 경우
	        userList = service.searchUsersWithPaging(keyword.trim(), filterType, page);
	        totalPage = service.getSearchResultPageCount(keyword.trim(), filterType);
	    } else {
	        // 검색 조건이 없으면 권한 기반 일반 조회
	        userList = service.getPagedUserList(userRole, page);
	        totalPage = service.getTotalPageCount(userRole);
	    }

	    // 페이징 블록 계산
	    int blockSize = 10;
	    int startPage = ((page - 1) / blockSize) * blockSize + 1;
	    int endPage = Math.min(startPage + blockSize - 1, totalPage);

	    // request attribute 설정
	    request.setAttribute("userList", userList);
	    request.setAttribute("selectedRole", userRole);
	    request.setAttribute("currentPage", page);
	    request.setAttribute("startPage", startPage);
	    request.setAttribute("endPage", endPage);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("keyword", keyword);
	    request.setAttribute("filterType", filterType);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/userInfoList.jsp");
	    dispatcher.forward(request, response);
	}
}