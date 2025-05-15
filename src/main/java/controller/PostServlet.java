package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NoticeDAO;
import dto.NoticeDTO;

@WebServlet("/post")
public class PostServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// 게시글 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int id = Integer.parseInt(request.getParameter("id"));
		String loginUserID = (String) session.getAttribute("userID");
	    String loginUserRole = (String) session.getAttribute("role"); // 예: 관리자, 교직원, 학생, 교수
		
		NoticeDAO dao = new NoticeDAO();
	    NoticeDTO post = dao.getNoticeByID(id); 
	    request.setAttribute("post", post);
	    
	    boolean isAuthor = loginUserID != null && loginUserID.equals(post.getUserID());
	    boolean isAdmin = loginUserRole != null && loginUserRole.equals("관리자");
	    boolean canDelete = isAuthor || isAdmin;
	    
	    // 권한 관련 플래그 JSP로 전달
	    request.setAttribute("isAuthor", isAuthor);
	    request.setAttribute("isAdmin", isAdmin);
	    request.setAttribute("canDelete", canDelete);
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/common/postpage.jsp");
	    dispatcher.forward(request, response);       // JSP로 전달
	    
	
	}
	
	// 게시글 작성 폼으로 이동
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    String userID = (String) session.getAttribute("userID");
	    String permissionRole = (String) session.getAttribute("permissionRole");

	    request.setAttribute("userID", userID);
	    request.setAttribute("permissionRole", permissionRole);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/common/writePost.jsp");
	    dispatcher.forward(request, response);
	}
}
