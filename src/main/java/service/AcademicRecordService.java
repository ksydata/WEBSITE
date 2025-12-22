package service;
//AcademicRecord과 AcademicRecordDAO 사이에서 비즈니스 로직

import dao.AcademicRecordDAO;
import dto.AcademicRecordDTO;

/*
 * 1. 기본 권한별 정보 조회기능 3가지 분기 외에 [v]
 * 2. 교수의 단과대학 학생 정보 수정 기능 및 
 * 3. 검증 기능 필요
 * 4. 페이징 로직 limit, offset 변수 추가 이후 academicRecordServlet, PagingService와 연계 필요 
 */

// [AS-IS] 개인정보 일부(전화번호, 주민등록번호 뒷자리) 마스킹할 때는 서비스단에서 처리
public class AcademicRecordService {
	private AcademicRecordDAO academicRecordDAO;
 
	// 개인정보 조회 메서드
	// 생성자: userInfoDAO 객체를 초기화 (DB 접근을 위해 필요)
	public AcademicRecordService() {
		academicRecordDAO = new AcademicRecordDAO();
	}
	
	// 학번/사번(userID)를 받아 개인정보를 가져오는 서비스 메서드
 public AcademicRecordDTO getAcademicRecord(String userID, String role, String college, 
		 int limit, int offset) {
     if (userID == null || userID.isEmpty()) {
         // userID가 비어있으면 null 반환
         return null;
     }
     
     // DAO를 통해 DB에서 사용자 권한별 학사정보 조회
     switch(role) {
    	 case "student":
    		 AcademicRecordDTO myRecord = (AcademicRecordDTO) academicRecordDAO.getRecordByStudent(userID);
    		 return myRecord;
    	 case "professor":
    		 AcademicRecordDTO classRecords = (AcademicRecordDTO) academicRecordDAO.getRecordsByCollege(college, limit, offset);
    		 return classRecords;
    	 case "admin":
    		 AcademicRecordDTO studentsRecords = (AcademicRecordDTO) academicRecordDAO.getTotalRecords(limit, offset);
    		 return studentsRecords;    		 
     }
	return null;
   }
}

/*
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
*/