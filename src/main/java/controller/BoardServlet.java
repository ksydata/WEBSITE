package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import dao.NoticeListDAO;
// import dao.NoticeControlDAO;
import dto.NoticeDTO;
import service.NoticeService;


/* GET요청: 게시판 공지글 전체 조회 (AS-IS: 페이징 기능 분리 전)
 * URL 파라미터 page를 기준으로 NoticeService를 통해 
 * 해당 페이지의 게시글 목록(noticeList)과 페이징 정보(currentPage, startPage, endPage, totalPage), 
 * URL 파라미터 정보 (pageURL, paramStr)를 조회해 postlist.jsp로 전달
 */
@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 자바객체 직렬화
	
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
	    request.setAttribute("totalPage", totalPages);   
	    // paging.jsp에서 이 이름 사용
	    request.setAttribute("startPage", startPage);
	    request.setAttribute("endPage", endPage);

	    request.setAttribute("pageURL", "board");
	    request.setAttribute("paramStr", ""); 
	    // 추가 파라미터가 없는 경우

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/common/postlist.jsp");
	    dispatcher.forward(request, response);
	}
}
