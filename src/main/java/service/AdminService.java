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
    
    // 유저 리스트 정렬 기능 작동 (userInfoList.jsp)
    public List<AdminPersonalInfoDTO> getUserListWithSorting(String role, int page, int pageSize, String sortOrder, String orderField) {
    	AdminListDAO dao = new AdminListDAO();
    	int offset = (page - 1) * pageSize;
        return dao.getUserListByRoleWithPagingAndSorting(role, offset, pageSize, sortOrder, orderField);
    }

    // 유저 수 세기
    public int getTotalUserCount(String role) {
    	AdminListDAO dao = new AdminListDAO();
        return dao.countUsersByRole(role);
    }
    
    // 관리자 본인 개인정보 가져오는 서비스 메서드
    public AdminPersonalInfoDTO getAdminInfo(String userID) {
    	if (userID == null || userID.isEmpty()) {
            // userID가 비어있으면 null 반환
            return null;
        }
        // DAO를 통해 DB에서 관리자 정보 조회
    	AdminPageDAO adminDAO = new AdminPageDAO();
    	AdminPersonalInfoDTO admin = adminDAO.getMyInfo(userID);

        // 교수 정보가 존재할 경우, 민감 정보 일부를 마스킹 처리
        if (admin != null) {
            // 전화번호 뒷 4자리 마스킹 (예: 010-1234-****)
            // ProfessorDTO.getter method            
        	String phone = admin.getPhoneNumber();
            if (phone != null && phone.length() >= 4) {
                String maskedPhoneNum = phone.substring(0, phone.length() - 4) + "****";
                // ProfessorDTO.setter method
                admin.setPhoneNumber(maskedPhoneNum);
            }

            // 주민등록번호 뒷 6자리 마스킹 (예: 010101-1******)
            // ProfessorDTO.getter method            
            String resident = admin.getResidentNumber();
            if (resident != null && resident.length() >= 7) {
                String maskedResidentNum = resident.substring(0, 7) + "******";
                // ProfessorDTO.setter method
                admin.setResidentNumber(maskedResidentNum);
            }
        }
        
        // ProfessorDTO의 객체인 학생 1명의 정보를 리턴
        return admin;
    }
    
}