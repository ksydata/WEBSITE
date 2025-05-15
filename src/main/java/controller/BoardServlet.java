package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NoticeDAO;
import dto.NoticeDTO;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// notice 리스트 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
        List<NoticeDTO> list = dao.getAllNotices();  // 모든 게시글 조회
        
        request.setAttribute("noticeList", list);    // request에 저장
        RequestDispatcher dispatcher = request.getRequestDispatcher("/common/postlist.jsp");
        dispatcher.forward(request, response);       // JSP로 전달
	}
	
	// 개별 notice 작성 및 등록
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글 처리
        request.setCharacterEncoding("UTF-8");

        // 폼에서 받은 데이터 추출
        String userID = request.getParameter("userID");
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");
        String endDate = request.getParameter("endDate");
        String permissionRole = request.getParameter("permissionRole");

        // DB에 저장
        NoticeDAO dao = new NoticeDAO();

        // DB 저장 결과로 noticeID (PK, auto increment) 반환
        int newNoticeID = dao.uploadNotice(userID, title, contents, endDate, permissionRole);

        if (newNoticeID > 0) {
            request.getSession().setAttribute("flashMessage", "게시글이 성공적으로 등록되었습니다.");
            response.sendRedirect(request.getContextPath() + "/board");
        } else {
            request.setAttribute("errorMessage", "게시글 등록에 실패했습니다.");
            request.getRequestDispatcher("common/writePost.jsp").forward(request, response);
        }
        
    }
	
}
