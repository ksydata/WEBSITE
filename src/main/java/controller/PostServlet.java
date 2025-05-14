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
	    
//	    RequestDispatcher dispatcher = request.getRequestDispatcher("/common/postlist.jsp?id=" + id);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/common/postpage.jsp");
	    dispatcher.forward(request, response);       // JSP로 전달
	    
	
	}
}
