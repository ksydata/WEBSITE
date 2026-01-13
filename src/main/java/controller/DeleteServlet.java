package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.NoticeListDAO;
import dao.NoticeControlDAO;
import dao.NoticeDAO;
import dto.NoticeDTO;

/* POST요청: 공지글 삭제 처리
 * 요청 파라미터 id로 삭제 대상 게시글(NoticeDTO)을 조회하고, 세션의 userID와 userRole을 이용해 작성자(isAuthor) 또는 관리자(isAdmin) 여부를 판단
 * 권한 검증 통과 시 NoticeControlDAO.deleteNotice(id)를 호출해 DB에서 게시글을 삭제
 * postpage.jsp의 삭제 버튼을 통해 성공 시 세션에 flashMessage를 설정하여 목록 페이지(/board)로 리다이렉트하며, 
 * 실패 또는 권한 없을 경우 경고창 출력 후 이전 페이지로 돌아감
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    int id = Integer.parseInt(request.getParameter("id"));
	    HttpSession session = request.getSession();
	    String sessionUserID = (String) session.getAttribute("userID");
	    String loginUserRole = (String) session.getAttribute("userRole");
	    // userRole: 학생, 교수, 교직원, 관리자 

	    // [TO-BE]
	    NoticeDAO noticeDAO = new NoticeDAO();
	    NoticeDTO post = noticeDAO.getNoticeByID(id);
	    
	    // [AS-IS]
//	    NoticeListDAO listdao = new NoticeListDAO();
//	    NoticeControlDAO controldao = new NoticeControlDAO();	    
//	    NoticeDTO post = listdao.getNoticeByID(id);
	    // 글쓴이 아이디에 따라 특정 게시판 공지글 관련 데이터를 조회하는 post 객체
	    // post.getUserID();를 통해 글쓴이 아이디 가져올 때 활용
	    // UPDATE NOTICE SET title = ?, contents = ?, updateDate = NOW() WHERE noticeID = ?;

	    boolean isAuthor = sessionUserID != null && sessionUserID.equals(post.getUserID());
	    boolean isAdmin = loginUserRole != null && (
	        loginUserRole.equals("관리자") || loginUserRole.equalsIgnoreCase("admin")
	    );
	    // 공지글 작성자 또는 관리자인지 확인하는 바이너리 변수(맞으면 true, 아니면 false)

	    if (!isAuthor && !isAdmin) {
	    	// 논리곱 연산자(모두 참이어야 true): 작성자가 아니면서 관리자가 아닌 조건
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().write("<script>alert('삭제 권한이 없습니다.'); history.back();</script>");
	        // 공지글 삭제 권한이 없다는 경고를 jsp 반환
	        return;
	    }

	    boolean result = noticeDAO.deleteNotice(id);
//	    [AS-IS] boolean result = controldao.deleteNotice(id);
	    // 작성자이거나 관리자라면, 게시판 내 특정 공지글 삭제
	    // DELETE FROM NOTICE WHERE noticeID = ?;
	    
	    if (result) {
	        session.setAttribute("flashMessage", "게시글이 삭제되었습니다.");
	        // 공지글이 삭제되었다는 결과를 jsp 반환
	        response.sendRedirect(request.getContextPath() + "/board");
	    } else {
	        response.setContentType("text/html; charset=UTF-8");
	        response.getWriter().write("<script>alert('삭제에 실패했습니다.'); history.back();</script>");
	    }
	}
}