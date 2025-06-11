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
import service.NoticeService;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// notice 리스트 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 페이징 파라미터 처리
	    int page = 1;
	    String pageParam = request.getParameter("page");
	    if (pageParam != null && pageParam.matches("\\d+")) {
	        page = Integer.parseInt(pageParam);
	    }

	    NoticeService service = new NoticeService();
	    List<NoticeDTO> list = service.getPagedNotices(page);
	    int totalPages = service.getTotalPages();

	    // 페이징 블록 계산
	    int blockSize = 10;
	    int startPage = ((page - 1) / blockSize) * blockSize + 1;
	    int endPage = Math.min(startPage + blockSize - 1, totalPages);

	    // 공통 페이징 관련 attribute 설정
	    request.setAttribute("noticeList", list);
	    request.setAttribute("currentPage", page);
	    request.setAttribute("totalPage", totalPages);   // paging.jsp에서 이 이름 사용
	    request.setAttribute("startPage", startPage);
	    request.setAttribute("endPage", endPage);

	    request.setAttribute("pageURL", "board");
	    request.setAttribute("paramStr", ""); // 추가 파라미터가 없는 경우

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/common/postlist.jsp");
	    dispatcher.forward(request, response);
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
