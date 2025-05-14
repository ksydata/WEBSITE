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

	    NoticeDAO dao = new NoticeDAO();
	    NoticeDTO post = dao.getNoticeByID(id);

	    if (!sessionUserID.equals(post.getUserID())) {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().write("<script>alert('올바른 계정으로 접근하십시오.'); history.back();</script>");
	        return;
	    }

	    boolean result = dao.deleteNotice(id);

	    if (result) {
	        // flashMessage는 세션에 저장하고, board 서블릿으로 리다이렉트
	        session.setAttribute("flashMessage", "게시글이 삭제되었습니다.");
	        response.sendRedirect(request.getContextPath() + "/board");
	    } else {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().write("<script>alert('삭제에 실패했습니다.'); history.back();</script>");
	    }
	}
}