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

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
	// 자바객체 직렬화
	
	/* GET요청: 공지글 수정폼 진입
	 * 요청 파라미터 id로 NoticeListDAO를 통해 해당 게시글(NoticeDTO)을 조회하고, 
	 * 세션의 userID와 게시글 작성자 일치 여부를 검증한 뒤 일치 시 
	 * 게시글 데이터를 request에 담아 editPost.jsp로 전달 및 postpage.jsp의 수정 버튼 생성, 리다이렉트
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        // 요청 파라미터에서 공지글 일련번호 추출
        HttpSession session = request.getSession();
        // 서버에서는 사용자 로그인 정보를 저장해둔 세션값
        String sessionUserID = (String) session.getAttribute("userID");
        // 세션에서 로그인된 사용자 아이디 가져오기
        
        // [TO-BE] 글쓴이 아이디에 따라 특정 게시판 공지글 관련 데이터를 조회하는 post 객체
        NoticeService noticeService = new NoticeService();
	    NoticeDTO post = noticeService.getNotice(id);
        

        if (sessionUserID == null || !sessionUserID.equals(post.getUserID())) {
            // 사용자 아이디가 공백값이거나 해당 공지글 작성자가 아닌 경우
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>alert('올바른 계정으로 접근하십시오.'); history.back();</script>");
            // 경고 메시지 반환 후 이전 페이지로 이동
            return;
        }

        request.setAttribute("post", post);
        // 아이디 검증하여, 사용자가 작성자 본인일 경우(관리자 권한: 공지글 수정 불가, 삭제 가능)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/common/editPost.jsp");
        dispatcher.forward(request, response);
        // 데이터를 요청값에 담아 공지글 수정 페이지인 editPost.jsp로 포워딩
        // <form action="${pageContext.request.contextPath}/EditServlet" method="post">
    }

    /* POST요청: 공지글 수정 처리
    * 수정폼에서 전달된 id, title, contents를 받아 세션의 userID와 원본 게시글 작성자가 동일한지 
    * 다시 확인하고, 검증 통과 시 NoticeControlDAO.updateNotice()로 DB를 수정한 뒤
    * 수정된 게시글 상세 페이지(/post?id=...)로 리다이렉트
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        // UTF-8 인코딩을 통해 한글 처리

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        // 수정폼(editPost.jsp)에서 전달된 공지글 일련번호, 제목, 내용 추출
        
        HttpSession session = request.getSession();
        String sessionUserID = (String) session.getAttribute("userID");
        
        // [TO-BE]
        NoticeService noticeService = new NoticeService();
	    NoticeDTO originalPost = noticeService.getNotice(id);
        
        if (sessionUserID == null || !sessionUserID.equals(originalPost.getUserID())) {
            // 사용자 아이디가 공백값이거나 해당 공지글 작성자가 아닌 경우 (Refactoring Point: doGet()과 중복)
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().println("<script>alert('올바른 계정으로 접근하십시오.'); history.back();</script>");
            // 경고 메시지 반환 후 이전 페이지로 이동            
            return;
        }
        
        noticeService.updatePost(id, title, contents);
        response.sendRedirect(request.getContextPath() + "/post?id=" + id);
        // 수정 후 수정된 공지글 조회(상세) 페이지인 postpage.jsp로 이동
    }
}