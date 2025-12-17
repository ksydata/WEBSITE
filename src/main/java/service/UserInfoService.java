package service;
//StudentInfoServlet과 StudentDAO 사이에서 비즈니스 로직

import dao.UserInfoDAO;
import dto.UserInfoDTO;

// 개인정보 일부(전화번호, 주민등록번호 뒷자리) 마스킹할 때는 서비스단에서 처리
public class UserInfoService {
	private UserInfoDAO userInfoDAO;
    
	// 개인정보 조회 메서드
	// 생성자: userInfoDAO 객체를 초기화 (DB 접근을 위해 필요)
	public UserInfoService() {
		userInfoDAO = new UserInfoDAO();
	}
	
	// 학번/사번(userID)를 받아 개인정보를 가져오는 서비스 메서드
    public UserInfoDTO getUserInfo(String userID) {
        if (userID == null || userID.isEmpty()) {
            // userID가 비어있으면 null 반환
            return null;
        }
        // DAO를 통해 DB에서 사용자 정보 조회
        UserInfoDTO userInfo = userInfoDAO.getMyInfo(userID);

        // 사용자 정보가 존재할 경우, 민감 정보 일부를 마스킹 처리
        if (userInfo != null) {
            // 전화번호 뒷 4자리 마스킹 (예: 010-1234-****)
            // userInfoDTO.getter method            
        	String phone = userInfo.getPhoneNumber();
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
        }
        
        // userInfoDTO의 객체인 사용자 1명의 정보를 리턴
        return userInfo;
    }
    
    // 개인정보 수정 메서드
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
}