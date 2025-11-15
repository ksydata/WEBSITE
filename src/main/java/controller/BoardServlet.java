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
import service.PagingService;


/* GET요청: 게시판 공지글 전체 조회 (TO-BE: '페이징 기능'과 '전체 리스트 조회 기능' 분리
 * URL 파라미터 page를 기준으로 NoticeService를 통해 
 * 해당 페이지의 게시글 목록(noticeList)과 페이징 정보(currentPage, startPage, endPage, totalPage), 
 * URL 파라미터 정보 (pageURL, paramStr)를 조회해 postlist.jsp로 전달
 */
@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 자바객체 직렬화
	private PagingService pagingService = new PagingService();
	// 공지사항 게시판 Notice 관련 (페이징) 비즈니스 로직을 처리하는 서비스 객체 선언
	
	// notice 리스트 조회
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String pageParam = request.getParameter("page");
	    int page = (pageParam != null && pageParam.matches("\\d+")) ? Integer.parseInt(pageParam) : 1;
	    // 요청 파라미터(page) 처리, 기본값은 1로 설정

	    List<NoticeDTO> list = pagingService.getPage(page);
	    // 현재 한 페이지당 보여줄 공지글 목록 조회
	    int totalPages = pagingService.getPage();
	    // 전체 페이지 수 계산

	    // [AS-IS] PagingDTO 객체를 사용하지 소스코드 반영 필요
	    // paging.jsp에서 요구하는 변수를 BoardServlet에서 재사용하는 방식으로 수정
	    // ${startPage}, ${endPage}, ${currentPage}, ${totalPage}, ${pageURL}, ${paramStr}	    
	    
	    // postlist.jsp로 전달할 데이터 설정
	    request.setAttribute("noticeList", list);
	    // 현제 페이지의 공지글 리스트
	    request.setAttribute("currentPage", page);
	    // 현재 페이지 번호
	    request.setAttribute("totalPage", totalPages);   
	    // 전체 페이지 수

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/common/postlist.jsp");
	    dispatcher.forward(request, response);
	    // postlist.jsp로 포워딩
	}
}
