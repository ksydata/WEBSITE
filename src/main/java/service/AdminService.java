package service;

import dao.AdminDAO;
import dto.AdminPersonalInfoDTO;

import java.util.List;

public class AdminService {

    private static final int PAGE_SIZE = 20;

    public List<AdminPersonalInfoDTO> getPagedUserList(String role, int page) {
        AdminDAO dao = new AdminDAO();
        int offset = (page - 1) * PAGE_SIZE;
        return dao.getUserListByRoleWithPaging(role, offset, PAGE_SIZE);
    }

    public int getTotalPageCount(String role) {
        AdminDAO dao = new AdminDAO();
        int totalUsers = dao.countUsersByRole(role);
        return (int) Math.ceil((double) totalUsers / PAGE_SIZE);
    }
    
    // 검색 + 페이징
    public List<AdminPersonalInfoDTO> searchUsersWithPaging(String keyword, String filterType, int page) {
    	AdminDAO dao = new AdminDAO();
    	int offset = (page - 1) * PAGE_SIZE;
        return dao.searchUsersWithPaging(keyword, filterType, offset, PAGE_SIZE);
    }

    // 검색 결과 수 기반 페이지 수 계산
    public int getSearchResultPageCount(String keyword, String filterType) {
    	AdminDAO dao = new AdminDAO();
        int totalResults = dao.countUsersBySearch(keyword, filterType);
        return (int) Math.ceil((double) totalResults / PAGE_SIZE);
    }
}