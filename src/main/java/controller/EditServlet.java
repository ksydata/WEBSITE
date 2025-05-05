package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NoticeDAO;
import dto.NoticeDTO;

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
	    
	    int id = Integer.parseInt(request.getParameter("id"));
	    String title = request.getParameter("title");
	    String contents = request.getParameter("contents");

	    HttpSession session = request.getSession();
	    String sessionUserID = (String) session.getAttribute("userID");

	    NoticeDAO dao = new NoticeDAO();
	    NoticeDTO originalPost = dao.getNoticeByID(id);

	    // 작성자 확인
	    if (sessionUserID == null || !sessionUserID.equals(originalPost.getUserID())) {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().println("<script>alert('올바른 계정으로 접근하십시오.'); history.back();</script>");
	        return;
	    }

	    // 수정 처리
	    dao.updateNotice(id, title, contents);

	    // 수정 후 해당 게시글 상세 페이지로 이동
        response.sendRedirect("common/postpage.jsp?id=" + id);
	}
	
}
