package service;

import dao.NoticeListDAO;
import dto.NoticeDTO;

import java.util.List;

public class NoticeService {

    // 한 페이지에 보여줄 공지사항 수 (100개의 글 중 20개만 조회) 
    private static final int PAGE_SIZE = 20;

    public List<NoticeDTO> getPagedNotices(int page) {
    	// DAO 객체 생성
        NoticeListDAO dao = new NoticeListDAO();
        // 현재 페이지 번호: 요청된 페이지 번호에 해당하는 공지사항 목록을 반환
        // offset: DB에서 몇번째부터 데이터를 가져올지 계산
        // 예) page = 3번째 장이면, (3-1)*20 = 40번째 글부터 가져오기       
        int offset = (page - 1) * PAGE_SIZE;
        return dao.getNoticesWithPaging(offset, PAGE_SIZE);
    }

    public int getTotalPages() {
        NoticeListDAO dao = new NoticeListDAO();
        int totalNotices = dao.getTotalNoticeCount();
        // 전체 공지사항 수를 기준으로 소수점 반올림한 값으로 총 페이지 수 계산
        // 예) 105개의 글 중 페이지당 20개씩 조회 시 총 6페이지 생성
        return (int) Math.ceil((double) totalNotices / PAGE_SIZE);
    }
}