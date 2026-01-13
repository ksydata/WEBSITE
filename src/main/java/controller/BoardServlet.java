package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.NoticeDTO;
import dto.PagingDTO;
import service.NoticeService;

// @https://wintmoca.tistory.com/42

/*
 * GET요청: 게시판 공지글 전체 조회 (AS-IS)
 * URL 파라미터 page를 기준으로 PagingService.getPage()를 통해
 * PagingDTO 객체와 해당 페이지의 게시글 목록(noticeList)과 페이징 정보(currentPage, startPage, endPage, totalPage), 
 * URL 파라미터 정보 (pageURL, paramStr)를 조회해 postlist.jsp로 전달

 */
@WebServlet("/board")
public class BoardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private NoticeService noticeService = new NoticeService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 페이지 파라미터 처리
        int page = 1;
        // 첫 페이지로 변수 초기화
        try { 
        	page = Integer.parseInt(request.getParameter("page"));
        	// http 요청에서 받은 페이지 파라미터 정수로 반환
        	// getAttribute();는 object(Data, File 타입)형으로 값 반환하여 추후 활용
        } catch(Exception ignored) {}
        
        
        // RowMapper 정의, 페이징 및 글 목록 조회를 noticeService.getNoticeList 를 통해 실행
        PagingDTO<NoticeDTO> pagingObject = noticeService.getNoticeList(page);
        
        // JSP(View)로 데이터 전달
        request.setAttribute("noticeList", pagingObject.getPagingDataList());
        
        // 개별 페이징 값 JSP로 전달
        request.setAttribute("currentPage", pagingObject.getCurrentPage());
        request.setAttribute("startPage", pagingObject.getStartPage());
        request.setAttribute("endPage", pagingObject.getEndPage());
        request.setAttribute("totalPage", pagingObject.getTotalPage());
        request.setAttribute("totalCount", pagingObject.getTotalCount());
        request.setAttribute("pageSize", pagingObject.getPageSize());

        request.getRequestDispatcher("/common/notice/postlist.jsp").forward(request, response);
    }
}

/*
 * [AS-IS] 과거 BoardServlet에 정의되었던 PagingService-PagingDAO 사용 공지 목록 조회 코드
 * // 2. RowMapper 정의
        // RowMapper 안에 NoticeDTO를 채운다는 아이디어를 살리되, 형태를 약간 바꾸어 resultSet 내부에 NoticeDTO를 정의하고 내용을 채움
        PagingDAO.RowMapper<NoticeDTO> mapper = resultSet -> {
            NoticeDTO dto = new NoticeDTO();
            // 공지사항 게시글 번호
            dto.setNoticeID(resultSet.getInt("noticeID"));
            // 글 제목
            dto.setTitle(resultSet.getString("title"));
            // 글 내용
            dto.setContents(resultSet.getString("contents"));
            // 작성자 아이디(학번/사번)
            dto.setUserID(resultSet.getString("userID"));
            // 작성일자
            dto.setCreateDate(resultSet.getTimestamp("createDate"));
            return dto;
        };
        
        
        // 3. 페이징 및 글 목록 조회
        PagingDTO<NoticeDTO> pagingObject = pagingService.getPage(
        		"NOTICE",
        		null,
        		"noticeID DESC",
        		page,
        		mapper
        		// The method getPage(String, String, String, int, PagingDAO.RowMapper<T>) in the type PagingService is not applicable for the arguments (String, null, String, int, int)
        );
 */