package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NoticeListDAO;
// import dao.NoticeControlDAO;
import dto.NoticeDTO;

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
	    String loginUserRole = (String) session.getAttribute("userRole"); 
	    // userRole: 학생, 교수, 교직원, 관리자 
	    
	    NoticeListDAO listdao = new NoticeListDAO();
	    // NoticeControlDAO controldao = new NoticeControlDAO();
	    NoticeDTO post = listdao.getNoticeByID(id);
	    // 글쓴이 아이디에 따라 특정 게시판 공지글 관련 데이터를 조회하는 post 객체
	    // SELECT * FROM NOTICE WHERE noticeID = ?;
	    request.setAttribute("post", post);
	    
	    boolean isAuthor = loginUserID != null && loginUserID.equals(post.getUserID());
	    // 논리곱 연산자를 통해 사용자 아이디가 공백값이 아니면서 + 현재 접속한 사용자 아이디와 작성자 아이디가 일치할 경우 true
	    boolean isAdmin = loginUserRole != null && loginUserRole.equals("관리자");
	    // 논리곱 연산자를 통해 사용자 아이디가 공백값이 아니면서 + 권한이 관리자일 경우 true
	    boolean canDelete = isAuthor || isAdmin;
	    // 해당 공지글 작성자이거나 관리자 계정일 경우를 조건으로 하는 플래그 생성 
	    
	    request.setAttribute("isAuthor", isAuthor);
	    request.setAttribute("isAdmin", isAdmin);
	    request.setAttribute("canDelete", canDelete);
	    // 권한 관련 플래그를 jsp(자바 서버페이지)로 전달
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("common/notice/postpage.jsp");
	    dispatcher.forward(request, response);
	}
}
