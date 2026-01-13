package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import dao.NoticeListDAO;
import dao.NoticeControlDAO;
// import dto.NoticeDTO;
import dao.NoticeDAO;

/* 공지글을 작성하고 등록하는 WriteServlet.java 컨트롤러 생성
 * BoardServlet.java에서 공지사항 전체 리스트 보여주기와 개별 공지 포스트 등록하기 기능이 1개 서블릿에 doGet(), doPost() 메서드로 묶여 있음
 * PostServlet.java에서 개별 게시글 조회 기능과 게시글 작성 폼으로 이동하는 기능이 1개 서블릿에 doGet(), doPost() 메서드로 묶여 있음
 * 개별 포스트 등록 폼으로 이동하는 기능 doGet() 과 포스트를 등록하는 기능 doPost() 을 WriteServlet.java 로 분리
 */
@WebServlet("/write")
public class WriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 자바객체 직렬화
	
	/* GET요청: 공지글 작성폼 이동
	 * 세션에서 userID, permissionRole을 가져와 작성 폼(writePost.jsp)에 전달함으로써 
	 * 로그인 사용자 정보를 반영한 글쓰기 페이지로 이동
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    String userID = (String) session.getAttribute("userID");
	    String permissionRole = (String) session.getAttribute("permissionRole");
	    // 게시글 작성 권한의 유효성 확인을 위해 사용자 아이디와 계정 권한정보를 세션값 생성
	    // 서버에서는 사용자 로그인 정보를 세션에 저장해두고, 요청이 들어올 때마다 세션값 기준으로 처리
	    // (위험) String userID = request.getParameter("userID");
	    
	    request.setAttribute("userID", userID);
	    request.setAttribute("permissionRole", permissionRole);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("common/notice/writePost.jsp");
	    dispatcher.forward(request, response);
	    // 세션값을 writePost.jsp 히든 필드 <input type="hidden"> 로 전달
	    // 단, 브라우저 개발자도구(F12)를 통해 누구나 값을 확인하고 수정 가능한 보안 취약점 대비 
	}
	
	/* POST요청: 개별 공지글 작성 및 등록
	 * 작성폼에서 전달된 userID, title, contents, endDate, permissionRole을 받아 NoticeDAO로 DB에 저장하고,
	 * 저장 성공 시 세션의 flashMessage를 설정하여 목록 페이지로 리다이렉트하거나 실패 시 오류 메시지를 JSP로 전달
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // UTF-8 인코딩을 통해 한글 처리

        String userID = request.getParameter("userID");
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        String endDate = request.getParameter("endDate");
        String permissionRole = request.getParameter("permissionRole");
        // 공지글 작성폼(writePost.jsp)에서 받은 데이터 추출
        
        
        // [TO-BE]
        NoticeDAO noticeDAO = new NoticeDAO();
        int newNoticeID = noticeDAO.uploadNotice(userID, title, contents, endDate, permissionRole);
        
        // [AS-IS]
//	    NoticeControlDAO controldao = new NoticeControlDAO();
	    // 공지 작성을 위한 DAO 객체 생성
	    // INSERT INTO NOTICE (userID, title, contents, createDate, updateDate, endDate, permissionRole) VALUES (?, ?, ?, ?, ?, ?, ?);
	    // NoticeListDAO listdao = new NoticeListDAO();

//        int newNoticeID = controldao.uploadNotice(userID, title, contents, endDate, permissionRole);
        // 공지글 작성자(현 사용자) 아이디, 제목, 내용, 종료일자, 권한정보를 NOTICE 테이블에 삽입
        // DB에 저장한 결과로 PK(noticeID, auto increment) 반환

        if (newNoticeID > 0) {
        	// 새로운 공지글 일련번호가 생성된 경우
            request.getSession().setAttribute("flashMessage", "게시글이 성공적으로 등록되었습니다.");
            response.sendRedirect(request.getContextPath() + "/board");
        } else {
        	// 공지글 등록에 실패한 경우
            request.setAttribute("errorMessage", "게시글 등록에 실패했습니다.");
            request.getRequestDispatcher("common/notice/writePost.jsp").forward(request, response);
        }
    }	
}
