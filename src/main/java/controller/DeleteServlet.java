package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.NoticeDAO;
import dto.NoticeDTO;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    int id = Integer.parseInt(request.getParameter("id"));

	    HttpSession session = request.getSession();
	    String sessionUserID = (String) session.getAttribute("userID");
	    String loginUserRole = (String) session.getAttribute("userRole"); // 예: 관리자, 교직원, 학생, 교수

	    NoticeDAO dao = new NoticeDAO();
	    NoticeDTO post = dao.getNoticeByID(id);

	    // 작성자 또는 관리자만 삭제 가능
	    boolean isAuthor = sessionUserID != null && sessionUserID.equals(post.getUserID());
	    boolean isAdmin = loginUserRole != null && (
	        loginUserRole.equals("관리자") || loginUserRole.equalsIgnoreCase("admin")
	    );

	    if (!isAuthor && !isAdmin) {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().write("<script>alert('삭제 권한이 없습니다.'); history.back();</script>");
	        return;
	    }

	    boolean result = dao.deleteNotice(id);

	    if (result) {
	        session.setAttribute("flashMessage", "게시글이 삭제되었습니다.");
	        response.sendRedirect(request.getContextPath() + "/board");
	    } else {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().write("<script>alert('삭제에 실패했습니다.'); history.back();</script>");
	    }
	}
}