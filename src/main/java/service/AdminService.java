package service;

import dao.AdminListDAO;
import dao.AdminPageDAO;
import dao.AdminSearchDAO;
import dto.AdminPersonalInfoDTO;
import dto.AdminRecordDTO;

import java.util.List;

public class AdminService {

    private static final int PAGE_SIZE = 20;

    // 페이징 조회
    public List<AdminPersonalInfoDTO> getPagedUserList(String role, int page) {
    	AdminListDAO dao = new AdminListDAO();
        int offset = (page - 1) * PAGE_SIZE;
        return dao.getUserListByRoleWithPaging(role, offset, PAGE_SIZE);
    }

    // 페이지 수 세기
    public int getTotalPageCount(String role) {
    	AdminListDAO dao = new AdminListDAO();
        int totalUsers = dao.countUsersByRole(role);
        return (int) Math.ceil((double) totalUsers / PAGE_SIZE);
    }
    
    // 검색 + 페이징
    public List<AdminPersonalInfoDTO> searchUsersWithPaging(String keyword, String filterType, int page) {
    	AdminSearchDAO dao = new AdminSearchDAO();
    	int offset = (page - 1) * PAGE_SIZE;
        return dao.searchUsersWithPaging(keyword, filterType, offset, PAGE_SIZE);
    }

    // 검색 결과 수 기반 페이지 수 계산
    public int getSearchResultPageCount(String keyword, String filterType) {
    	AdminSearchDAO dao = new AdminSearchDAO();
        int totalResults = dao.countUsersBySearch(keyword, filterType);
        return (int) Math.ceil((double) totalResults / PAGE_SIZE);
    }
    
    // 성적 상세 페이지 출력
    public List<AdminRecordDTO> getRecordsByStudent(String userID) {
    	AdminPageDAO dao = new AdminPageDAO();
    	return dao.getRecordsByStudent(userID);
    }
    
    // 개인정보 상세 페이지 출력
    public AdminPersonalInfoDTO getUserInfo(String userID) {
    	AdminPageDAO dao = new AdminPageDAO();
    	return dao.getUserInfo(userID);
    }
    
}