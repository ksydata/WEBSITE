package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.NoticeDTO;
import service.NoticeService;

/* GET요청: 특정 공지글 상세 조회
 * 요청 파라미터 id로 NoticeListDAO에서 게시글(NoticeDTO)을 조회하고,
 * 세션의 userID, userRole을 이용해 작성자·관리자 여부(isAuthor, isAdmin, canDelete)를 
 * 판단하여 request로 post와 함께 postpage.jsp로 전달
 */
@WebServlet("/post")
public class PostServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	// 자바객체 직렬화
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int id = Integer.parseInt(request.getParameter("id"));
		String loginUserID = (String) session.getAttribute("userID");
//	    String loginUserRole = (String) session.getAttribute("userRole"); 
	    // userRole: 학생, 교수, 교직원, 관리자 
	    
	    NoticeService noticeService = new NoticeService();
	    NoticeDTO post = noticeService.getNotice(id);
	    request.setAttribute("post", post);
	    // 글쓴이 아이디에 따라 특정 게시판 공지글 관련 데이터를 조회하는 post 객체
	    // SELECT * FROM NOTICE WHERE noticeID = ?;
	    
	    
	    boolean isAuthor = loginUserID != null && loginUserID.equals(post.getUserID());
	    request.setAttribute("isAuthor", isAuthor);
	    // 논리곱 연산자를 통해 사용자 아이디가 공백값이 아니면서 + 현재 접속한 사용자 아이디와 작성자 아이디가 일치할 경우 true

	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("common/notice/postpage.jsp");
	    dispatcher.forward(request, response);
	}
}
