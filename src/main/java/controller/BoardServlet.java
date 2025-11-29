package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PagingDAO;
import dto.NoticeDTO;
import dto.PagingDTO;
import service.PagingService;

// @https://wintmoca.tistory.com/42

/*
 * GET요청: 게시판 공지글 전체 조회 (AS-IS)
 * URL 파라미터 page를 기준으로 PagingService.getPage()를 통해
 * PagingDTO 객체와 해당 페이지의 게시글 목록(noticeList)과 페이징 정보(currentPage, startPage, endPage, totalPage), 
 * URL 파라미터 정보 (pageURL, paramStr)를 조회해 postlist.jsp로 전달

 */
@WebServlet("/board")
public class BoardServlet extends HttpServlet {

    private PagingService pagingService = new PagingService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 페이지 파라미터 처리
        int page = 1;
        // 첫 페이지로 변수 초기화
        try { 
        	page = Integer.parseInt(request.getParameter("page"));
        	// http 요청에서 받은 페이지 파라미터 정수로 반환
        	// getAttribute();는 object(Data, File 타입)형으로 값 반환하여 추후 활용
        } catch(Exception ignored) {}

        // 2. RowMapper 정의
        /* [AS-IS]
        PagingDAO.RowMapper<NoticeDTO> mapper = resultSet -> new NoticeDTO(
        		resultSet.getInt("noticeID"),
        		// 공지사항 게시글 번호
        		resultSet.getString("title"),
        		// 글 제목
        		resultSet.getString("content"),
        		// 글 내용
        		resultSet.getString("userID"),
        		// 작성자 아이디(학번/사번)
        		resultSet.getInt("createDate")
        		// 
        );
        */
        int mapper = 1;
        
        // 3. 페이징 및 글 목록 조회
        PagingDTO<NoticeDTO> pagingObject = pagingService.getPage(
        		"NOTICE",
        		null,
        		"noticeID DESC",
        		page,
        		mapper
        		// The method getPage(String, String, String, int, PagingDAO.RowMapper<T>) in the type PagingService is not applicable for the arguments (String, null, String, int, int)
        );
        
        // 4. JSP(View)로 데이터 전달
        // request.setAttribute("noticeList", paging.getPagingDataList());
        

        request.getRequestDispatcher("/common/postlist.jsp").forward(request, response);
    }
}

/*
 * GET요청: 게시판 공지글 전체 조회 (TO-BE: '페이징 기능'과 '전체 리스트 조회 기능' 분리
 * URL 파라미터 page를 기준으로 NoticeService를 통해 
 * 해당 페이지의 게시글 목록(noticeList)과 페이징 정보(currentPage, startPage, endPage, totalPage), 
 * URL 파라미터 정보 (pageURL, paramStr)를 조회해 postlist.jsp로 전달

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
*/