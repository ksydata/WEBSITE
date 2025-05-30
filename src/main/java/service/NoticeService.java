package service;

import dao.NoticeDAO;
import dto.NoticeDTO;

import java.util.List;

public class NoticeService {

    private static final int PAGE_SIZE = 20;

    public List<NoticeDTO> getPagedNotices(int page) {
        NoticeDAO dao = new NoticeDAO();
        int offset = (page - 1) * PAGE_SIZE;
        return dao.getNoticesWithPaging(offset, PAGE_SIZE);
    }

    public int getTotalPages() {
        NoticeDAO dao = new NoticeDAO();
        int totalNotices = dao.getTotalNoticeCount();
        return (int) Math.ceil((double) totalNotices / PAGE_SIZE);
    }
}