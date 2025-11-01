package service;

import dao.NoticeListDAO;
import dto.NoticeDTO;
import dto.PagingDTO;
// import dto.AdminPersonalInfoDTO;
import java.util.List;

// @https://keep-programming-study.tistory.com/125
// @https://blog.naver.com/poodoli2000/223061150709
// @https://gyuggling.tistory.com/396

public class NoticeService {

    private static final int PAGE_SIZE = 20;
    // 한 페이지에 보여줄 공지사항 수 (100개의 글 중 20개만 조회)
    private final NoticeListDAO listdao = new NoticeListDAO();
    // 공지글 데이터 접근 객체(DAO)
    
    // 한 페이지에 보여줄 공지글 목록과 수 반환하는 메서드
    public List<NoticeDTO> getPagedNotices(int page) {
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
    
    // 게시판 목록에 표시할 번호 계산하는 메서드
    public PagingDTO getPagingInfo(int currentPage, int pagingBlockSize) {
        int totalPage = getTotalPages();

        int startPage = ((currentPage - 1) / pagingBlockSize) * pagingBlockSize + 1;
        // pagination에서 보이는, 시작하는 첫 페이지 번호 계산
        // 예) 3번째 페이지, 페이지당 게시물 10개일 경우 (3-1)*10 + 1 = 21 (21번째 게시물부터)
        int endPage = Math.min(startPage + pagingBlockSize - 1, totalPage);
        // pagination에서 보이는, 끝나는 마지막 페이지 번호 계산
        // 예) 3번째 페이지, 페이지당 10개일 경우 21+10-1과 전체 페이지수 30 중 더 작은 값인 30 (30번째 게시물까지)

        return new PagingDTO(currentPage, totalPage, startPage, endPage, pagingBlockSize);
        // service method를 pagination을 위한 Page 객체로 조회하고, 이를 PageDTO로 변환하여 반환
    }
    
    // 이전/다음 페이지 블록 바로가기 기능 <, >
    // 첫/마지막 페이지 블록 바로가기 기능 <<, >>
}