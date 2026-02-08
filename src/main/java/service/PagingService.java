package service;

import dao.PagingDAO;
import dao.PagingDAO.RowMapper;
import dto.AcademicRecordDTO;
import dto.PagingDTO;
import java.util.List;

// @https://keep-programming-study.tistory.com/125
// @https://blog.naver.com/poodoli2000/223061150709
// @https://gyuggling.tistory.com/396

public class PagingService {
	
	private final PagingDAO pagingdao = new PagingDAO();
	// 공지글 데이터 접근 객체(DAO)
	private static final int PAGE_SIZE = 20;
	// 한 페이지에 보여줄 공지사항 수 (100개의 글 중 20개만 조회)
	private static final int PAGING_BLOCK_SIZE = 10; 
	// 페이징 블록 크기

	public <T> PagingDTO<T> getPage(
			String TABLE_NAME, String WHERE_CLAUSE, String ORDER_BY,
			int currentPage, RowMapper<T> Mapper) {
	    
		if(currentPage < 1) currentPage = 1;
		// 현재 페이지 1보다 작을 경우 1로 값 초기화
		int totalCount = pagingdao.getTotalCount(TABLE_NAME, WHERE_CLAUSE);
		// PagingDAO.java를 통해 전체 데이터 수 집계한 값 반환
		
		int totalPage = (int)Math.ceil( (double)totalCount / PAGE_SIZE ); 
	    // 전체 페이지 수
	    int startPage = ((currentPage - 1) / PAGING_BLOCK_SIZE) * PAGING_BLOCK_SIZE + 1; 
	    // 현재 블록의 시작 페이지
	    // pagination에서 보이는, 시작하는 첫 페이지 번호 계산
	    // 예) 3번째 페이지, 페이지당 게시물 10개일 경우 (3-1)*10 + 1 = 21 (21번째 게시물부터)
	    int endPage = Math.min(startPage + PAGING_BLOCK_SIZE - 1, totalPage); 
	    // 현재 블록의 마지막 페이지
	    // pagination에서 보이는, 끝나는 마지막 페이지 번호 계산
	    // 예) 3번째 페이지, 페이지당 10개일 경우 21+10-1과 전체 페이지수 30 중 더 작은 값인 30 (30번째 게시물까지)
		
	    int OFFSET = (currentPage - 1) * PAGE_SIZE;
	    // 
        List<T> list = pagingdao.getPagingDataList(
        		TABLE_NAME, WHERE_CLAUSE, ORDER_BY, 
        		OFFSET, PAGE_SIZE, Mapper);
        // 
		
		PagingDTO<T> pagingDTO = new PagingDTO<>();
	    // 
		pagingDTO.setCurrentPage(currentPage);
	    pagingDTO.setTotalPage(totalPage);
	    pagingDTO.setStartPage(startPage);
	    pagingDTO.setEndPage(endPage);
	    pagingDTO.setPageSize(PAGE_SIZE);
	    pagingDTO.setTotalCount(totalCount);
	    pagingDTO.setPagingDataList(list);
	    
		return pagingDTO;
	}
	
	// [AS-IS] 이전/다음 페이지 블록 바로가기 기능 <, >
	// [AS-IS] 첫/마지막 페이지 블록 바로가기 기능 <<, >>  
}


/*
// 게시판 목록에 표시할 번호 계산하는 생성자(공통 모듈)
// [AS-IS] BoardServlet(/board → postlist.jsp) → [TO-BE] PagingDTO → paging.jsp
public PagingDTO(
		int currentPage, int totalCount, int pageSize, int totalPage, int startPage, int endPage, int pagingBlockSize) {
    
    this.currentPage = currentPage; // 현재 페이지 번호
    this.totalCount = totalCount; // 전체 데이터 수
    this.pageSize = pageSize; // 한 페이지당 표시할 항목 수
    this.pagingBlockSize = pagingBlockSize; // 페이징 바에 보여줄 페이지 수(페이징 블록)
    
    this.totalPage = (int)Math.ceil( (double)totalCount / pageSize ); // 전체 페이지 수
    this.startRow = ((currentPage - 1) * pageSize); // LIMIT 시작 위치
    
    this.

  
}

public class NoticeService {

	private static final int PAGE_SIZE = 20;
	// 한 페이지에 보여줄 공지사항 수 (100개의 글 중 20개만 조회)
	private final NoticeListDAO listdao = new NoticeListDAO();
	// 공지글 데이터 접근 객체(DAO)
	
	// 한 페이지에 보여줄 공지글 목록과 수 반환하는 메서드
	public List<PagingDTO> getTotalCount(int page) {
		if (page < 1) page = 1;
		// 페이지 번호가 0 이하인 예외 처리를 위한 보정
	    int offset = (page - 1) * PAGE_SIZE;
	    return listdao.getNoticesWithPaging(offset, PAGE_SIZE);
	    // 현재 페이지 번호: 요청된 페이지 번호에 해당하는 공지사항 목록을 반환
	    // offset: DB에서 몇번째부터 데이터를 가져올지 계산
	    // 예) page = 3번째 장이면, (3-1)*20 = 40번째 글부터 가져오기  
	}
	
	// 전체 페이지 수 계산하는 메서드
	public int getTotalPages() {
	    int totalNotices = listdao.getTotalNoticeCount();
	    // 전체 공지사항 수를 기준으로 소수점 반올림한 값으로 총 페이지 수 계산
	    // 예) 105개의 글 중 페이지당 20개씩 조회 시 총 6페이지 생성
	    return (int) Math.ceil((double) totalNotices / PAGE_SIZE);
	}
}
*/