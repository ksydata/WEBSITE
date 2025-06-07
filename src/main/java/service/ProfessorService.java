package service;
//StudentInfoServlet과 StudentDAO 사이에서 비즈니스 로직

import dao.ProfessorDAO;
import dto.ProfessorDTO;

// 개인정보 일부(전화번호, 주민등록번호 뒷자리) 마스킹할 때는 서비스단에서 처리
public class ProfessorService {
	private ProfessorDAO professorDAO;
    
	// 1. 개인정보 조회 메서드
	// 생성자: ProfessorDAO 객체를 초기화 (DB 접근을 위해 필요)
	public ProfessorService() {
		professorDAO = new ProfessorDAO();
	}
	
	// 학번/사번(userID)를 받아 개인정보를 가져오는 서비스 메서드
    public ProfessorDTO getProfessorInfo(String userID) {
        if (userID == null || userID.isEmpty()) {
            // userID가 비어있으면 null 반환
            return null;
        }
        // DAO를 통해 DB에서 교수 정보 조회
        ProfessorDTO professor = professorDAO.getMyInfo(userID);

        // 교수 정보가 존재할 경우, 민감 정보 일부를 마스킹 처리
        if (professor != null) {
            // 전화번호 뒷 4자리 마스킹 (예: 010-1234-****)
            // ProfessorDTO.getter method            
        	String phone = professor.getPhoneNumber();
            if (phone != null && phone.length() >= 4) {
                String maskedPhoneNum = phone.substring(0, phone.length() - 4) + "****";
                // ProfessorDTO.setter method
                professor.setPhoneNumber(maskedPhoneNum);
            }

            // 주민등록번호 뒷 6자리 마스킹 (예: 010101-1******)
            // ProfessorDTO.getter method            
            String resident = professor.getResidentNumber();
            if (resident != null && resident.length() >= 7) {
                String maskedResidentNum = resident.substring(0, 7) + "******";
                // ProfessorDTO.setter method
                professor.setResidentNumber(maskedResidentNum);
            }
        }
        
        // ProfessorDTO의 객체인 학생 1명의 정보를 리턴
        return professor;
    }
    
    // 개인정보 수정 메서드
    public void updateProfessorInfo(String userID, String phoneNumber, String officeNumber, String email, String address) {
    	// 휴대전화번호 수정
    	if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
    		professorDAO.updatePhoneNumber(userID, phoneNumber);
    	}
    	// 사무실전화번호 수정
    	if (officeNumber != null && !officeNumber.trim().isEmpty()) {
    		professorDAO.updateOfficeNumber(userID, officeNumber);
    	}
    	// 이메일 수정
    	if (email != null && !email.trim().isEmpty()) {
    		professorDAO.updateEmail(userID, email);
    	}
    	// 주소 수정
    	if (address != null && !address.trim().isEmpty()) {
    		professorDAO.updateAddress(userID, address);
    	}
    }
    
    // 비밀번호 검증 메서드 (verify: 과정 중심의 시스템 검증)
    public boolean verifyCurrentPassword(String userID, String inputPassword) {
    	return professorDAO.checkCurrentPassword(userID, inputPassword);
    }
    
    // 비밀번호 변경 메서드
    public void updateProfessorPW(String userID, String userPassword) {
    	// 데이터접근객체에서 비밀번호 변경 메서드 적용
    	if (userPassword != null && !userPassword.trim().isEmpty()) {
    		professorDAO.updatePassword(userID, userPassword);
    	}
    }
}