package service;
// UserInfoServlet과 UserInfoDAO 사이에서 비즈니스 로직

import dao.UserInfoDAO;
import dto.UserInfoDTO;
import dto.PagingDTO;
import util.RoleEnum;

/*
 * 1. 기본 권한별 정보 조회기능 2가지 분기 (본인, 관리자)
 * 2. 본인 개인정보 수정 기능
 * 3. 본인 비밀번호 검증 및 변경 기능
 * 4. 페이징 로직 limit, offset 변수 추가 이후 UserInfoServlet, PagingService와 연계
 */

// @https://kimsaemjava.tistory.com/240

// 개인정보 일부(전화번호, 주민등록번호 뒷자리) 마스킹할 때는 서비스단에서 처리
public class UserInfoService {
	private UserInfoDAO userInfoDAO;
	// UserInformation(USER/PERSONAL_INFO 테이블)을 CRUD(조회/수정)하기 위한 접근 객체	
	private PagingService pagingService;
	// Notice, AcademicRecord와 같이 비즈니스 로직 처리 시 페이징 추가
	
	// 생성자: userInfoDAO/PagingService 객체를 초기화 (DB 접근을 위해 필요)
	public UserInfoService() {
		userInfoDAO = new UserInfoDAO();
		pagingService = new PagingService();
	}
	
	// 학번/사번(userID)를 받아 개인정보를 가져오는 서비스 메서드
    // [AS-IS] 개인정보 조회 메서드(Role 분기)
    public void getUserInfo(String userID, RoleEnum role, int currentPage) {
		if (userID == null || userID.isEmpty()) {
			// @WebFilter(gate keeper)에서 접근 가능성 검증하지만, Service에서 또 쓰이는 이유는 
			// Service는 권한별 비즈니스 로직 실행에 대해 다른 Servlet 등에서도 호출될 수 있기 때문에 의도적으로 중복			
			throw new IllegalArgumentException("학번/사번이 유효하지 않습니다.");
		}
        
        // DAO를 통해 DB에서 사용자 정보 조회
		UserInfoDTO userInfo = userInfoDAO.getMyInfo(userID).get(0);
		// getMyInfo는 List<UserInfoDTO>를 반환하므로 첫 번째 요소 추출

        // 사용자 정보가 존재할 경우, 민감 정보 일부를 마스킹 처리
        if (userInfo != null) {
            // 전화번호 뒷 4자리 마스킹 (예: 010-1234-****)
            // userInfoDTO.getter method            
        	String phone = userInfo.getPhoneNumber();
        	// userInfo.getPhoneNumber().replaceAll("(\\d{3}-\\d{4})-\\d{4}", "$1-****")

            if (phone != null && phone.length() >= 4) {
                String maskedPhoneNum = phone.substring(0, phone.length() - 4) + "****";
                // userInfoDTO.setter method
                userInfo.setPhoneNumber(maskedPhoneNum);
            }

            // 주민등록번호 뒷 6자리 마스킹 (예: 010101-1******)
            // userInfoDTO.getter method            
            String resident = userInfo.getResidentNumber();
            if (resident != null && resident.length() >= 7) {
                String maskedResidentNum = resident.substring(0, 9) + "******";
                // userInfoDTO.setter method
                userInfo.setResidentNumber(maskedResidentNum);
            }
            
    	    // DAO를 통해 DB에서 사용자 권한별 학사정보 조회
    	    switch(role) {
    	    	case ROLE_001:	 
    	     	// case "student":
    	    		getUserInfo(userID);
    	            // userInfoDTO의 객체인 사용자 1명의 정보를 리턴
    	    	case ROLE_002:
    	    		getUserInfo(userID);
    	    	case ROLE_004:
    	    		getTotalUserInfo(currentPage);
    	    	default:
    	    		break;    		 
    	     }
        }        
    }
    
    // 개인정보 수정 메서드
	public void updateUserInfo(String userID, String phoneNumber, String officeNumber, 
			String email, String address) {
		// DAO를 통해 개인정보 수정 (동적 쿼리로 변경된 필드만 업데이트)
		userInfoDAO.updateUserInfo(userID, phoneNumber, officeNumber, email, address);
	}
		
    /* [AS-IS] 개인정보 수정 메서드
    public void updateUserInfo(String userID, String phoneNumber, String officeNumber, String email, String address) {
    	// 휴대전화번호 수정
    	if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
    		userInfoDAO.updatePhoneNumber(userID, phoneNumber);
    	}
    	// 사무실전화번호 수정
    	if (officeNumber != null && !officeNumber.trim().isEmpty()) {
    		userInfoDAO.updateOfficeNumber(userID, officeNumber);
    	}
    	// 이메일 수정
    	if (email != null && !email.trim().isEmpty()) {
    		userInfoDAO.updateEmail(userID, email);
    	}
    	// 주소 수정
    	if (address != null && !address.trim().isEmpty()) {
    		userInfoDAO.updateAddress(userID, address);
    	}
    }
    */
    
    // 비밀번호 검증 메서드 (verify: 과정 중심의 시스템 검증)
    public boolean verifyCurrentPassword(String userID, String inputPassword) {
    	return userInfoDAO.checkCurrentPassword(userID, inputPassword);
    }
    
    // 비밀번호 변경 메서드
    public void updateUserInfoPW(String userID, String userPassword) {
    	// 데이터접근객체에서 비밀번호 변경 메서드 적용
    	if (userPassword != null && !userPassword.trim().isEmpty()) {
    		userInfoDAO.updatePassword(userID, userPassword);
    	}
    }
    
	// 본인의 개인정보 조회하는 정적 메서드(RowMapper 활용을 위해 존재)
    private PagingDTO<UserInfoDTO> getUserInfo(String userID) {
		String TABLE = "USER U LEFT JOIN PERSONAL_INFO P ON U.userID = P.userID";
		// DB 테이블
		String WHERE = "userID = '" + userID + "'";
		// SELECT 쿼리 조건절(학번 검색)
    	String ORDER = null;
    	
    	return pagingService.getPage(
    			TABLE, WHERE, ORDER, 1, UserInfoDAO.ROW_MAPPER);
    }
    
    // 관리자의 전체 사용자의 개인정보 조회하는 정적 메서드(페이징 적용)
    private PagingDTO<UserInfoDTO> getTotalUserInfo(int currentPage) {
    	String TABLE = "USER U LEFT JOIN PERSONAL_INFO P ON U.userID = P.userID";
		// DB 테이블(조인)    	
    	String WHERE = null;
		// SELECT 쿼리 조건절    	
    	String ORDER = "U.userID ASC";
    	// 학번/사번 순으로 오름정렬
    	
    	return pagingService.getPage(
    			TABLE, WHERE, ORDER, currentPage, UserInfoDAO.ROW_MAPPER);
    }
}